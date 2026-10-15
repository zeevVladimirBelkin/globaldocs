/*
 * ChannelReader.java
 *
 * Created on July 13, 2006, 11:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package globaldocs.io;

import globaldocs.io.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.*;

/**
 *
 * @author zeev
 */
public class ChannelOutputStream extends OutputStream {
    WritableByteChannel wbc;
      
    /** Creates a new instance of ChannelReader */
    public ChannelOutputStream(WritableByteChannel wbc) {
      this.wbc=wbc;
    }
    
    public void write(int b) throws IOException {
      wbc.write(ByteBuffer.wrap(new byte[] {(byte)b}));
    }
    
    public void write(byte b[], int off, int len) throws IOException {
      wbc.write(ByteBuffer.wrap(b,off,len));
    }
}
