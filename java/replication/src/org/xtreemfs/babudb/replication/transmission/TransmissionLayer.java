/*
 * Copyright (c) 2010 - 2011, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Felix Langner, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
 */
/*
 * AUTHORS: Felix Langner (ZIB)
 */
package org.xtreemfs.babudb.replication.transmission;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.xtreemfs.babudb.config.ReplicationConfig;
import org.xtreemfs.babudb.replication.Layer;
import org.xtreemfs.babudb.replication.RemoteAccessClient;
import org.xtreemfs.babudb.replication.transmission.dispatcher.RequestDispatcher;
import org.xtreemfs.babudb.replication.transmission.dispatcher.RequestHandler;
import org.xtreemfs.foundation.LifeCycleListener;
import org.xtreemfs.foundation.pbrpc.client.RPCNIOSocketClient;

/**
 * <p>
 * Abstraction of the transmission facilities used for the replication.
 * Includes interfaces for the layer above. Implements {@link Layer}.
 * </p>
 * 
 * @author flangner
 * @since 04/12/2010
 */
public class TransmissionLayer extends Layer implements ClientFactory, 
        TransmissionToServiceInterface {
        
    /** low level client for outgoing RPCs */
    private final RPCNIOSocketClient    rpcClient;
    
    /** dispatcher to process incoming RPCs */
    private final RequestDispatcher     dispatcher;
        
    /** interface for accessing files defined by BabuDB */
    private final FileIO                fileIO;
    
    /**
     * @param config
     * 
     * @throws IOException if the {@link RPCNIOSocketClient} could not be 
     *                     started.
     */
    public TransmissionLayer(ReplicationConfig config) 
            throws IOException {
        this.fileIO = new FileIO(config);
        
        // ---------------------------------
        // initialize the RPCNIOSocketClient
        // ---------------------------------
        this.rpcClient = new RPCNIOSocketClient(config.getSSLOptions(), 
                ReplicationConfig.REQUEST_TIMEOUT,
                ReplicationConfig.CONNECTION_TIMEOUT);
        
        // ---------------------------------
        // initialize the RequestDispatcher
        // ---------------------------------
        this.dispatcher = new RequestDispatcher(config);
    }
    
/*
 * Overridden methods
 */
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.
     * TransmissionToServiceInterface#getFileIOInterface()
     */
    @Override
    public FileIOInterface getFileIOInterface() {
        return this.fileIO;
    }
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.ClientFactory#
     * getClient(java.net.SocketAddress)
     */
    @Override
    public PBRPCClientAdapter getClient(SocketAddress receiver) {
        assert (receiver instanceof InetSocketAddress);
        
        return new PBRPCClientAdapter(this.rpcClient, 
                (InetSocketAddress) receiver);
    }
    
    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.Layer#
     * _setLifeCycleListener(org.xtreemfs.foundation.LifeCycleListener)
     */
    @Override
    public void _setLifeCycleListener(LifeCycleListener listener) {
        this.dispatcher.setLifeCycleListener(listener);
        this.rpcClient.setLifeCycleListener(listener);
    }
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.Layer#start()
     */
    @Override
    public void start() {
        try {
            this.dispatcher.start();
            this.dispatcher.waitForStartup();
            
            this.rpcClient.start();
            this.rpcClient.waitForStartup();
        } catch (Exception e) {
            this.listener.crashPerformed(e);
        }
    }
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.Layer#asyncShutdown()
     */
    @Override
    public void asyncShutdown() {
        this.dispatcher.shutdown();
        this.rpcClient.shutdown();
    }

    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.Layer#shutdown()
     */
    @Override
    public void shutdown() {
        try {    
            this.dispatcher.shutdown();
            this.dispatcher.waitForShutdown();
            
            this.rpcClient.shutdown();
            this.rpcClient.waitForShutdown();  
        } catch (Exception e) {
            this.listener.crashPerformed(e);
        }
    }

    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.TransmissionToServiceInterface#addRequestHandler(org.xtreemfs.babudb.replication.transmission.dispatcher.RequestHandler)
     */
    @Override
    public void addRequestHandler(RequestHandler handler) {
        dispatcher.addHandler(handler);
    }
    
    public RemoteAccessClient getRemoteAccessClient() {
        return new RemoteClientAdapter(rpcClient);
    }
}