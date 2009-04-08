/*
 * Copyright (c) 2009, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Felix Langner, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
 */
package org.xtreemfs.babudb.replication;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.xtreemfs.babudb.replication.ReplicationThread.ReplicationException;
import org.xtreemfs.include.common.logging.Logging;

import static org.xtreemfs.babudb.replication.Status.STATE.*;

/**
 * <p>Wrapper that adds a state to an object of T, that has to be requested.</p>
 * <p>Counts failed attempts to match retry-restriction and ACKs from broadcast requests.</p>
 * 
 * @author flangner
 * @param <T>
 */
class Status<T> {   
	static enum STATE {OPEN,PENDING,DONE}
	
    private             T               rq       = null;

    /** counter for failed attempts to process this {@link Request} */
    private final       AtomicInteger   failedAttempts          = new AtomicInteger(0);
    
    /** status of an broadCast request - the expected responses */
    private final       AtomicInteger   maxReceivableResp       = new AtomicInteger(1);
    private final       AtomicInteger   minExpectableResp       = new AtomicInteger(0);
    private final 		AtomicReference<STATE> state			= new AtomicReference<STATE>(OPEN);
    
    /** back-link to the queues */
    private final   ReplicationThread   statusListener;   
    
    /**
     * <p>A request dummy with the lowest priority for the pending queues.</p>
     */
    Status() {
        this(null,(ReplicationThread) null);
        this.state.set(PENDING);
    }
    
    /**
     * <p>A request dummy for the pending queues. Marked, as open (not pending).</p>
     * @param c
     */
    Status(T c){
    	this(c,(ReplicationThread) null);
    }
    
    /**
     * <p>A request dummy for the pending queues. With the given pending-state.</p>
     * @param c
     * @param pending
     */
    Status(T c,boolean pending){
    	this(c,(ReplicationThread) null);
    	if (pending) this.state.set(PENDING);
    }
    
    /**
     * <p>Saves a the given <code>c</code>.</p>
     * <p>Status will be OPEN.</p>
     * 
     * @param c
     * @param sL - the obsolete-status-listener
     */
    Status(T c, ReplicationThread sL) {
        this.rq = c;
        this.statusListener = sL;
    }

/*
 * getter/setter    
 */   
    boolean isPending(){
    	return this.state.get().equals(PENDING);
    }
    
    T getValue(){
        return rq;
    }
 
    /**
     * sets the maximal number of responses receivable by slaves
     * 
     * @param count
     */
    void setMaxReceivableResp(int count) {
        assert (count > 0) : "There has to be at least one receiver of the request.";
        maxReceivableResp.set(count);
    }
    
    /**
     * sets the number of minimal amount of expectable responses for a broad cast request to be successful
     *  
     * @param count
     */
    void setMinExpectableResp(int count) {
        minExpectableResp.set(count);
    }
    
    /**
     * <p>Sets the request's status to pending and reorders the queue it is in.</p>
     * @throws ReplicationException if status could not be changed.
     */
	void pending() throws ReplicationException {
		state.set(PENDING);
		statusListener.statusChanged(this);
	}    
    
    /**
     * <p>Resets the state of the actual request.</p>
     * @throws ReplicationException if status could not be changed.
     */
    void retry() throws ReplicationException {
    	state.set(OPEN);
    	maxReceivableResp.set(1);
    	minExpectableResp.set(0);
    	statusListener.statusChanged(this);
    }
    
    /**
     * <p>Function to wait synchronous for a {@link Request} to be done.</p>
     * @return <code>true</code> if minExpected is gt the maxReceivable. In other word, if the request has failed. false otherwise.
     */
    boolean waitFor() {
    	synchronized (state) {
    	    while (!state.get().equals(DONE)) {
		try {
		    state.wait();
		}catch (InterruptedException e) { /* ignored */ }
    	    }
	}
    	return minExpectableResp.get()>maxReceivableResp.get();
    }
/*
 * broadcast counters    
 */

    /**
     * Decreases the counter for receivable sub-requests by <code>count</code>.
     * 
     * @param count
     * @return true, if the maximal number of receivable responses is GE than the minimal number of ACKs expected by the application. false otherwise 
     */
    private boolean decreaseMaxReceivableResp() {
        int remaining = maxReceivableResp.decrementAndGet();       
        assert (remaining >= 0) : "There cannot be less than 0 expected receivable ACKs left. Especially not: "+remaining;       
        return remaining >= minExpectableResp.get();
    }

    /**
     * Decreases the counter for expectable sub-requests.
     * 
     * @return true, if counter was decreased to 0, false otherwise.
     */
    private boolean decreaseMinExpectableResp() {
        int remainingExpected = minExpectableResp.decrementAndGet();
        int remainingReceivable = maxReceivableResp.decrementAndGet();        
        return remainingExpected == 0 || (remainingReceivable == 0 && remainingExpected<0);
    }
    
/*
 * retry-methods    
 */
    
    /**
     * <p>Increases a inner failure-attempt-counter.</p>
     * 
     * @return true, if there is an attempt left.
     */
    private boolean attemptFailedAttemptLeft(int maxTries){
        int failed = this.failedAttempts.incrementAndGet();
        return (maxTries == 0) || (failed < maxTries);
    }
    
    /**
     * <p>If the request becomes obsolete it will be removed from the listeners queues.</p>
     * @throws ReplicationException if request could not be removed.
     */
    void cancel() throws ReplicationException {     
        assert(state.get().equals(PENDING)) : "An open request cannot be done!";
        assert (statusListener!=null) : "A dummy cannot be obsolete!";
        synchronized(state){
            if (!state.equals(DONE)) {
        	statusListener.remove(this);
        	state.set(DONE);
        	state.notifyAll();
            }
        }
    }
    
    /**
     * <p>If the request was finished any available listeners will be notified and it will be removed.</p>
     * 
     * @throws ReplicationException if the request could not be marked as finished.
     */
    void finished() throws ReplicationException {
        assert (statusListener!=null) : "A dummy cannot be finished!";
        if (state.get().equals(PENDING)) {
	        synchronized(state) {
	            if (!state.get().equals(DONE)) {
	                if (decreaseMinExpectableResp()) {  
	                    statusListener.finished(this);
	                    state.set(DONE);
	                    state.notifyAll();
	                }
	            }
	        }
        }
    }
    
    /**
     * <p>If the request has failed any available listeners will be notified and the reason will be delivered.</p>
     * <p>The request will be enqueued, if it has not finally failed, which means, that there are no more attempts left for retrying.</p>
     * 
     * @param reason
     * @param maxTries
     * @throws ReplicationException if the request could not be marked as failed.
     * @return true, if the request could be marked as failed, false if it was already marked as failed.
     */
    boolean failed(String reason,int maxTries) throws ReplicationException {
        assert (statusListener != null) : "A dummy cannot fail!";
        if (!state.get().equals(PENDING)) return false;
        synchronized(state) {
        	if (!state.get().equals(DONE)) {
        		// check, if it has completely failed
	            if (!decreaseMaxReceivableResp()) {
	            	// check, if there are retry-attempts left
	            	if (attemptFailedAttemptLeft(maxTries)) {     
	            		// retry
	            		Logging.logMessage(Logging.LEVEL_TRACE, this, "Request has failed, and will be retried soon...");
	            		retry();
	            	} else {
	            		// it has really failed
	            		Logging.logMessage(Logging.LEVEL_TRACE, this, "Giving up after '"+maxTries+"' attempts to: "+rq.toString());
	            		statusListener.failed(this,reason);
	            	        state.set(DONE);
	            	        state.notifyAll();
	            	}
	            }
        	} 
        }
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.lsmdb.LSN#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        Status<T> o = (Status<T>) obj;
        if (obj == null) return false;
        if (o.rq==null && rq==null) return true;
        if (o.rq==null) return false;
        return rq.equals(o.rq);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {  
        String string = state.get()+": "+((rq!=null) ? rq.toString() : "n.a.");
        if (!state.get().equals(OPEN))
            string+="This request failed for '"+failedAttempts.get()+"' times,";
        
        return string;       
    }
}