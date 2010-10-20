package com.ning.api.client.http;

import java.io.IOException;
import java.util.concurrent.Future;

import org.codehaus.jackson.map.ObjectMapper;

public abstract class NingRequestBuilder<T extends NingRequestBuilder<T>>
{
    // // Methods for all HTTP verbs:
    
    /**
     * Method for adding header to send with request
     */
    public abstract T addHeader(String header, String value);

    /**
     * Method for adding query parameter to append to URL
     */
    public abstract T addQueryParameter(String name, String value);

    /**
     * Method for building and sending request built by this builder.
     */
    public abstract Future<NingHttpResponse> sendRequest(ObjectMapper objectMapper)
        throws IOException;
    
    // Methods for things that can have payload (POST, PUT)

    public abstract T setBody(String body);

    public abstract T addFormParameter(String name, String value);

}
