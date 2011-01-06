/*
 * Copyright (c) 2009-2010, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Felix Langner, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
 */
package org.xtreemfs.babudb.replication.service.operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.xtreemfs.babudb.pbrpc.Common.emptyResponse;
import org.xtreemfs.babudb.pbrpc.GlobalTypes.Chunk;
import org.xtreemfs.babudb.pbrpc.ReplicationServiceConstants;
import org.xtreemfs.babudb.replication.transmission.dispatcher.Operation;
import org.xtreemfs.babudb.replication.transmission.dispatcher.Request;
import org.xtreemfs.babudb.replication.transmission.dispatcher.RequestDispatcher;
import org.xtreemfs.foundation.buffer.BufferPool;
import org.xtreemfs.foundation.buffer.ReusableBuffer;
import org.xtreemfs.foundation.logging.Logging;
import org.xtreemfs.foundation.pbrpc.generatedinterfaces.RPC.ErrorType;
import org.xtreemfs.foundation.pbrpc.generatedinterfaces.RPC.RPCHeader.ErrorResponse;
import org.xtreemfs.foundation.util.OutputUtils;

import com.google.protobuf.Message;

/**
 * {@link Operation} to request a {@link Chunk} from the master.
 * 
 * @since 05/03/2009
 * @author flangner
 */

public class ChunkOperation extends Operation {

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.service.operations.Operation#
     * getProcedureId()
     */
    @Override
    public int getProcedureId() {
        return ReplicationServiceConstants.PROC_ID_CHUNK;
    }
    
    /* (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.transmission.dispatcher.Operation#getDefaultRequest()
     */
    @Override
    public Message getDefaultRequest() {
        return Chunk.getDefaultInstance();
    }

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.service.operations.Operation#
     * startInternalEvent(java.lang.Object[])
     */
    @Override
    public void startInternalEvent(Object[] args) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * @see org.xtreemfs.babudb.replication.service.operations.Operation#
     * startRequest(org.xtreemfs.babudb.replication.Request)
     */
    @Override
    public void startRequest(Request rq) {
        Chunk chunk = (Chunk) rq.getRequestMessage();
        int length = (int) (chunk.getEnd() - chunk.getStart());
      
        Logging.logMessage(Logging.LEVEL_INFO, this, 
                "%s request received from %s", chunk.toString(), 
                rq.getRPCRequest().getSenderAddress().toString());
        
        FileChannel channel = null;
        ReusableBuffer payload = null;
        try {
            // get the requested chunk
            channel = new FileInputStream(chunk.getFileName()).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(length);
            if (channel.read(buffer, chunk.getStart()) != length) 
                throw new Exception(); 
            
            buffer.flip();
            payload = new ReusableBuffer(buffer);
            rq.sendSuccess(emptyResponse.getDefaultInstance(), payload);
            
        } catch (Exception e) {
            // TODO fix exceptionhandling!
            rq.sendReplicationException(ErrNo.FILE_UNAVAILABLE, 
                    "The requested chunk ("+chunk.toString()+") is not" +
                    " available anymore, because: "+e.getMessage(), e);
            
            if (e.getMessage() == null) 
                Logging.logError(Logging.LEVEL_INFO, this, e);
        } finally {
            try {
                if (channel != null) channel.close();
            } catch (IOException e) { /* ignored */ }
            
            if (payload != null) BufferPool.free(payload);
        }
    }
}