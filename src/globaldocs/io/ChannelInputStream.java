/*
 * ChannelReader.java
 *
 * Created on July 13, 2006, 11:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package globaldocs.io;

import java.io.*;
import java.nio.channels.*;
import java.nio.*;

/**
 *
 * @author zeev
 */
public class ChannelInputStream extends InputStream {
    ReadableByteChannel rbc;
    
    ByteBuffer bb=ByteBuffer.allocate(4096);
    int avl=0,offset=0;
    
    /** Creates a new instance of ChannelReader */
    public ChannelInputStream(ReadableByteChannel rbc) {
      this.rbc=rbc;
      bb.mark();
    }
    
    public int read() throws java.io.IOException {
       if (avl==0) { bb.reset();  avl=rbc.read(bb); offset=0; }
       if (avl==0) return -1;
       --avl;
       byte b=bb.array()[offset++];
       if (avl==0) { bb.reset();  avl=rbc.read(bb); offset=0; }
       return b;
    }
    
    public int available() { return avl; }
}
