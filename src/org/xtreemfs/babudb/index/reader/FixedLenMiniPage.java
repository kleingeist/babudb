/*
 * Copyright (c) 2008, Jan Stender, Bjoern Kolbeck, Mikael Hoegqvist,
 *                     Felix Hupfeld, Zuse Institute Berlin
 * 
 * Licensed under the BSD License, see LICENSE file for details.
 * 
*/

package org.xtreemfs.babudb.index.reader;

import java.nio.ByteBuffer;

import org.xtreemfs.babudb.index.ByteRange;
import org.xtreemfs.babudb.index.ByteRangeComparator;
import org.xtreemfs.common.buffer.BufferPool;
import org.xtreemfs.common.buffer.ReusableBuffer;
import org.xtreemfs.common.util.OutputUtils;

public class FixedLenMiniPage extends MiniPage {
    
    private final int entrySize;
    
    private final int limit;
    
    public FixedLenMiniPage(int entrySize, int numEntries, ByteBuffer buf, int offset, int limit,
        ByteRangeComparator comp) {
        
        super(numEntries, buf, offset, comp);
        
        this.entrySize = entrySize;
        this.limit = limit;
    }
    
    public ByteRange getEntry(int n) {
        return new ByteRange(buf, offset + n * entrySize, offset + (n + 1) * entrySize);
    }
    
    public String toString() {
        
        buf.position(offset);
        buf.limit(limit);
        ReusableBuffer newBuf = BufferPool.allocate(limit - buf.position());
        newBuf.put(buf);
        String result = OutputUtils.byteArrayToFormattedHexString(newBuf.array());
        BufferPool.free(newBuf);
        buf.clear();
        
        return result;
    }
}