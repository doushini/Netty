package com.luo.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * netty没有提供\0截取。不过重写部分代码即可
 * @author 罗辉, @date:2013-7-25
 *
 */
public class Delimiters {

	 public static ChannelBuffer[] zeroDelimiter() {
        return new ChannelBuffer[] { 
        		ChannelBuffers.wrappedBuffer(new byte[] { '\0' }),ChannelBuffers.wrappedBuffer(new byte[] { '\r', '\n' }) 
        };
    }
 
    private Delimiters() {
 
    }
}
