package com.ning.api.client.auth;

import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;

/**
 * Wrapper class for (more) efficient UTF-8 encoding and decoding
 */
public class UTF8Codec
{
    // Crap: Android does not have methods needed....
    //private final Charset utf8;
    
    public UTF8Codec() {
        //utf8 = Charset.forName("UTF-8");
    }

    public byte[] toUTF8(String input) {
//        return input.getBytes(utf8);
        try {
            return input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoder not found");
        }
    }

    public String fromUTF8(byte[] input) {
        return fromUTF8(input, 0, input.length);
    }
    
    public String fromUTF8(byte[] input, int offset, int len) {
//        return new String(input, offset, len, utf8);
        try {
            return new String(input, offset, len, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 decoder not found");
        }
    }
}
