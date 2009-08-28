package org.xtreemfs.babudb.interfaces.ReplicationInterface;

import org.xtreemfs.babudb.*;
import org.xtreemfs.babudb.interfaces.*;
import java.util.HashMap;
import org.xtreemfs.babudb.interfaces.utils.*;
import org.xtreemfs.include.foundation.oncrpc.utils.ONCRPCBufferWriter;
import org.xtreemfs.include.common.buffer.ReusableBuffer;




public class loadRequest implements org.xtreemfs.babudb.interfaces.utils.Request
{
    public static final int TAG = 1013;

    
    public loadRequest() { lsn = new LSN(); }
    public loadRequest( LSN lsn ) { this.lsn = lsn; }
    public loadRequest( Object from_hash_map ) { lsn = new LSN(); this.deserialize( from_hash_map ); }
    public loadRequest( Object[] from_array ) { lsn = new LSN();this.deserialize( from_array ); }

    public LSN getLsn() { return lsn; }
    public void setLsn( LSN lsn ) { this.lsn = lsn; }

    // Object
    public String toString()
    {
        return "loadRequest( " + lsn.toString() + " )";
    }

    // Serializable
    public int getTag() { return 1013; }
    public String getTypeName() { return "org::xtreemfs::babudb::interfaces::ReplicationInterface::loadRequest"; }

    public void deserialize( Object from_hash_map )
    {
        this.deserialize( ( HashMap<String, Object> )from_hash_map );
    }
        
    public void deserialize( HashMap<String, Object> from_hash_map )
    {
        this.lsn.deserialize( from_hash_map.get( "lsn" ) );
    }
    
    public void deserialize( Object[] from_array )
    {
        this.lsn.deserialize( from_array[0] );        
    }

    public void deserialize( ReusableBuffer buf )
    {
        lsn = new LSN(); lsn.deserialize( buf );
    }

    public Object serialize()
    {
        HashMap<String, Object> to_hash_map = new HashMap<String, Object>();
        to_hash_map.put( "lsn", lsn.serialize() );
        return to_hash_map;        
    }

    public void serialize( ONCRPCBufferWriter writer ) 
    {
        lsn.serialize( writer );
    }
    
    public int calculateSize()
    {
        int my_size = 0;
        my_size += lsn.calculateSize();
        return my_size;
    }

    // Request
    public Response createDefaultResponse() { return new loadResponse(); }


    private LSN lsn;    

}
