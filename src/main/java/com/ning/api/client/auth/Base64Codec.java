package com.ning.api.client.auth;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class Base64Codec
{
    private final ObjectMapper objectMapper;
    
    public Base64Codec() { this(new ObjectMapper()); }
    public Base64Codec(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toBase64(byte[] input)
    {
        // Alas, can not use ObjectMapper.convertValue() before 1.5.6 is available,
        // due to bug [JACKSON-330]...
        try {
            String str = objectMapper.writeValueAsString(input);
            // will have double-quotes which we need to remove...
            return str.substring(1, str.length()-1);
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem base64 encoding user credentials for Basic AUTH: "+e.getMessage(), e);
        }
    }
}
