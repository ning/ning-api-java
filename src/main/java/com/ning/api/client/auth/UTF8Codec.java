package com.ning.api.client.auth;

import java.nio.charset.Charset;

/**
 * Wrapper class for (more) efficient UTF-8 encoding and decoding
 */
public class UTF8Codec
{
    private final Charset utf8;
    
    public UTF8Codec() {
        utf8 = Charset.forName("UTF-8");
    }

    public byte[] toUTF8(String input) {
        return input.getBytes(utf8);
    }

    public String fromUTF8(byte[] input) {
        return fromUTF8(input, 0, input.length);
    }
    
    public String fromUTF8(byte[] input, int offset, int len) {
        return new String(input, offset, len, utf8);
    }
}
