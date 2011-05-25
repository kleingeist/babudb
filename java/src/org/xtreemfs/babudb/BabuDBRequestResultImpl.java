/*
 * Copyright (c) 2009 - 2011, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Felix Langner, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
 */
/*
 * AUTHORS: Felix Langner (ZIB)
 */
package org.xtreemfs.babudb;

import java.util.concurrent.atomic.AtomicBoolean;

import org.xtreemfs.babudb.api.database.DatabaseRequestListener;
import org.xtreemfs.babudb.api.database.DatabaseRequestResult;
import org.xtreemfs.babudb.api.exception.BabuDBException;
import org.xtreemfs.babudb.api.exception.BabuDBException.ErrorCode;
import org.xtreemfs.babudb.lsmdb.LSN;

/**
 * Default return value for BabuDB requests.
 * 
 * @author flangner
 * @since 11/11/2009
 * @param <T>
 */
public class BabuDBRequestResultImpl<T> implements DatabaseRequestResult<T> {
    
    private DatabaseRequestListener<T>  listener; 

    private T                           result;
    
    private BabuDBException             error; 
    
    private final AtomicBoolean         finished = new AtomicBoolean(false);
    
    protected final Object              context;
    
    private LSN                         assignedLSN = null;
    
/*
 * constructors
 */
    
    /**
     * Creates a new future-object for a BabuDB request.
     */
    public BabuDBRequestResultImpl() {
        this.context = null;
    }
    
    /**
     * Creates a new future-object for a BabuDB request.
     * 
     * @param context
     *          can be null.
     */
    public BabuDBRequestResultImpl(Object context) {
        this.context = context;
    }
    
/*
 * getter/setter
 */
    
    public LSN getAssignedLSN() {
        return assignedLSN;
    }
    
/*
 * state changing methods    
 */
    
    /**
     * Internal operation to run if the request was finished without 
     * a return value.
     * Has to be invoked exactly one time!
     * 
     * @param lsn
     */
    public void finished(LSN lsn) {
        finished(null, null, lsn);
    }
    
    /**
     * Internal operation to run if the request was finished.
     * Has to be invoked exactly one time!
     * 
     * @param result
     * @param lsn
     */
    public void finished(T result) {
        finished(result, null, null);
    }
    
    /**
     * Internal operation to run if the request was finished.
     * Has to be invoked exactly one time!
     * 
     * @param result
     * @param lsn
     */
    public void finished(T result, LSN lsn) {
        finished(result, null, lsn);
    }
    
    /**
     * Internal operation to run if the request failed.
     * Has to be invoked exactly one time!
     * 
     * @param error - has to be not null, if an error occurs.
     */
    public void failed(BabuDBException error) {
        finished(null, error, null);
    }
    
/*
 * private methods    
 */
    
    /**
     * Internal operation to run if the request was finished.
     * Has to be invoked exactly one time!
     * 
     * @param result
     * @param error 
     *            has to be not null, if an error occurs.
     * @param lsn
     */
    private void finished(T result, BabuDBException error, LSN lsn) {
        assert (result == null || error == null) : "Results are not permitted on error!";
        this.error = error;
        this.result = result;
        this.assignedLSN = lsn;
        
        // notify the synchronously waiting instances
        synchronized (finished) {
            boolean check = finished.compareAndSet(false, true);
            assert (check) : "The request was already finished!";
            
            finished.notifyAll();
        }
        
        // notify the asynchronous-listener
        if (listener != null) {
            if (error != null) {
                listener.failed(error, context);
            } else {
                listener.finished(result, context);
            }
        }
    }

/*
 * DatabaseRequestResult interface
 */
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.api.database.DatabaseRequestResult#registerListener(org.xtreemfs.babudb.api.database.DatabaseRequestListener)
     */
    public void registerListener(DatabaseRequestListener<T> listener) {
        synchronized (finished) {
            assert (this.listener == null) : "There is already a listener registered!";
            if (finished.get()) {
                if (error == null) {
                    listener.finished(result,context);
                } else {
                    listener.failed(error,context);
                }
            } else {
                this.listener = listener;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.api.database.DatabaseRequestResult#get()
     */
    public T get() throws BabuDBException {
        try {
            synchronized (finished) {
                if (!finished.get()) {
                    finished.wait(); 
                }
            }
        } catch (InterruptedException e) {
            throw new BabuDBException(ErrorCode.INTERRUPTED, 
                    "Thread was interrupted while waiting for the response.");
        }
        if (error != null) throw error;
        return result;
    }
}
