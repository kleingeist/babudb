/*
 * Copyright (c) 2009-2010, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Felix Langner, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
 */
package org.xtreemfs.babudb.replication;

import java.net.InetAddress;

import org.xtreemfs.babudb.BabuDB;
import org.xtreemfs.babudb.config.ReplicationConfig;
import org.xtreemfs.babudb.log.LogEntry;
import org.xtreemfs.babudb.replication.control.ControlLayer;
import org.xtreemfs.babudb.replication.service.ServiceLayer;
import org.xtreemfs.babudb.replication.service.accounting.ReplicateResponse;
import org.xtreemfs.babudb.replication.transmission.TransmissionLayer;
import org.xtreemfs.foundation.TimeSync;
import org.xtreemfs.foundation.buffer.ReusableBuffer;
import org.xtreemfs.foundation.logging.Logging;

/**
 * <p>Implements the {@link ReplicationManager} user interface.</p>
 * <p>Configurable settings.</p>
 * <p>Used to be one single Instance.</p>
 * <p>Thread safe.</p>
 * 
 * @since 06/05/2009
 * @author flangner
 */

public class ReplicationManagerImpl implements ReplicationManager {
        
    public final static String  VERSION = "1.0.0 (v1.0 RC1)";
    
    private final TopLayer      controlLayer;
    private final Layer         serviceLayer;
    private final Layer         transmissionLayer;
    
    private volatile boolean    slaveCheck = false;
    
    /**
     * <p>For setting up the {@link BabuDB} with replication. 
     * Replication instance will be remaining stopped and in slave-mode.</p>
     * 
     * @param dbs
     * @throws Exception 
     */
    public ReplicationManagerImpl(BabuDB dbs) 
        throws Exception {
        ReplicationConfig conf = (ReplicationConfig) dbs.getConfig();
        TimeSync.initializeLocal(conf.getTimeSyncInterval(), 
                conf.getLocalTimeRenew()).setLifeCycleListener(this);

        TransmissionLayer t = new TransmissionLayer(conf);
        ServiceLayer s = new ServiceLayer(conf, new BabuDBInterface(dbs), t);
        ControlLayer c = new ControlLayer(s, conf);
        s.coin(c, c);
        
        this.transmissionLayer = t;
        this.serviceLayer = s;
        this.controlLayer = c;
        
        this.transmissionLayer.setLifeCycleListener(this);
        this.serviceLayer.setLifeCycleListener(this);
        this.controlLayer.setLifeCycleListener(this);
    }
    
/*
 * internal interface for BabuDB
 */
    
    /**
     * <p>Approach for a Worker to announce a new {@link LogEntry} <code>le</code> to the {@link ReplicationThread}.</p>
     * 
     * @param le - the original {@link LogEntry}.
     * @param buffer - the serialized {@link LogEntry}.
     * 
     * @return the {@link ReplicateResponse}.
     */
    public ReplicateResponse replicate(LogEntry le, ReusableBuffer buffer) {
        Logging.logMessage(Logging.LEVEL_DEBUG, this, "Performing requests: replicate...");
        
        return this.controlLayer.replicate(le, buffer);
    }
    
    /**
     * Starts the stages if available.
     */
    public void initialize() {
        this.controlLayer.start();
        this.serviceLayer.start();
        this.transmissionLayer.start();
        
        this.slaveCheck = true;
    }
    
    /**
     * <p>
     * Registers the listener for a replicate call.
     * </p>
     * 
     * @param response
     */
    public void subscribeListener(ReplicateResponse response) {
        this.controlLayer.subscribeListener(response);
    }
    
    /**
     * @return true if the replication mechanism already has been initialized,
     *         false otherwise.
     */
    public boolean isInitialized() {
        return this.slaveCheck;
    }
    
/*
 * Overridden methods
 */

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.ReplicationManager#getMaster()
     */
    @Override
    public InetAddress getMaster() {
        return this.controlLayer.getLeaseHolder();
    }
    
    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.ReplicationManager#isMaster()
     */
    @Override
    public boolean isMaster() {
        try {
            return this.controlLayer.hasLease();
        } catch (InterruptedException e) {
            Logging.logMessage(Logging.LEVEL_INFO, this, "IsMaster-method has" +
            		" been interrupted, a failover may be still in progress" +
            		", because: %s", e.getMessage());
            return false;
        }
    }

    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.ReplicationManager#shutdown()
     */
    @Override
    public void shutdown() throws Exception {
        this.controlLayer.shutdown();
        this.serviceLayer.shutdown();
        this.transmissionLayer.shutdown();
        TimeSync.getInstance().shutdown();
    }
    
    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.ReplicationManager#halt()
     */
    @Override
    public void manualFailover() {
        this.controlLayer.suspend();
    }
    
/*
 * LifeCycleListener for the TimeSync-Thread
 */
    
    /*
     * (non-Javadoc)
     * @see org.xtreemfs.foundation.LifeCycleListener#crashPerformed(java.lang.Throwable)
     */
    @Override
    public void crashPerformed(Throwable exc) {
        Logging.logMessage(Logging.LEVEL_CRIT, this, 
                "An essential replication component has crashed, because %s.",
                exc.getMessage());
        Logging.logError(Logging.LEVEL_CRIT, this, exc);
        
        this.controlLayer.suspend();
        this.controlLayer.asyncShutdown();
        this.serviceLayer.asyncShutdown();
        this.transmissionLayer.asyncShutdown();
        throw new RuntimeException(exc);
    }

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.foundation.LifeCycleListener#shutdownPerformed()
     */
    @Override
    public void shutdownPerformed() {}

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.foundation.LifeCycleListener#startupPerformed()
     */
    @Override
    public void startupPerformed() {}
}