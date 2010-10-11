package com.ning.api.client.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.http.client.Response;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.util.Base64;

import com.ning.api.client.NingClientException;
import com.ning.api.client.auth.UTF8Codec;
import com.ning.api.client.auth.UTF8UrlCodec;
import com.ning.api.client.exception.NingInterruptionException;
import com.ning.api.client.exception.NingProcessingException;
import com.ning.api.client.exception.NingTimeoutException;
import com.ning.api.client.exception.NingTransferException;
import com.ning.api.client.json.ExtendedObjectMapper;

public class NingHttpRequest<T extends NingHttpRequest<T>>
{
    /**
     * Helper class to simplify intermediate storing and passing of query and form
     * parameters
     */
    public static class Param
    {
        protected final String name;
        protected final String value;

        public Param(String name, String value)
        {
            this.name = name;
            this.value = value;
        }
    }
    
    public final static String HEADER_ACCEPT = "Accept";
    public final static String HEADER_AUTHORIZATION = "Authorization";
    public final static String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public final static String HEADER_CONTENT_TYPE = "Content-Type";
    
    public final static String CONTENT_TYPE_FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    // Helper objects we need

    protected final static ObjectMapper objectMapper = new ExtendedObjectMapper();
    
    private final static UTF8Codec utf8Codec = new UTF8Codec();
    private final static UTF8UrlCodec urlEncoder = new UTF8UrlCodec();

    protected BoundRequestBuilder requestBuilder;
    
    // // // Certain types we will only add at the end
    
    protected String contentType;
    
    protected String contentEncoding;

    protected NingHttpRequest(BoundRequestBuilder requestBuilder)
    {
        this.requestBuilder = requestBuilder;
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Methods for actually sending request
    /////////////////////////////////////////////////////////////////////////
    */

    /**
     * Synchronous call for executing all request types.
     *<p>
     * NOTE: creating async version with Future should be easy, if/when it is needed.
     */
    public NingHttpResponse execute(long timeoutMsecs) throws NingClientException
    {
        completeRequestBeforeExecute();
        Future<Response> future;
        try {
            future = requestBuilder.execute();
        } catch (IOException e) {
            return handleAsNingException(e);
        }

        try {
            return new NingHttpResponse(objectMapper, future.get(timeoutMsecs, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            throw new NingInterruptionException(e);
        } catch (ExecutionException e) {
            return handleAsNingException(e);
        } catch (TimeoutException e) {
            throw new NingTimeoutException(e, timeoutMsecs);
        }
    }
    
    protected void completeRequestBeforeExecute()
    {
        BoundRequestBuilder rb = requestBuilder;
        if (contentType != null) {
            rb = rb.addHeader(HEADER_CONTENT_TYPE, contentType);
        }
        if (contentEncoding != null) {
            rb = rb.addHeader(HEADER_CONTENT_ENCODING, contentEncoding);
        }
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Generic add methods
    /////////////////////////////////////////////////////////////////////////
    */
    
    public T addHeader(String name, String value)
    {
        requestBuilder = requestBuilder.addHeader(name, value);
        return _this();
    }

    public T addQueryParameter(String name, String value)
    {
        requestBuilder = requestBuilder.addQueryParameter(name, value);
        return _this();
    }

    public T addQueryParameter(Param p)
    {
        if (p != null) {
            addQueryParameter(p.name, p.value);
        }
        return _this();
    }

    public T addQueryParameters(Param... params)
    {
        if (params != null) {
            for (Param p : params) {
                addQueryParameter(p);
            }
        }
        return _this();
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Specific add/set methods
    /////////////////////////////////////////////////////////////////////////
    */

    public T setContentType(String type) {
        contentType = type;
        return _this();
    }

    public T setContentEncoding(String enc) {
        contentEncoding = enc;
        return _this();
    }
    
    public T addBasicAuth(String userName, String password)
    {
        String key = userName + ":" + password;
        String authAsBase64 = "Basic "+Base64.encode(utf8Codec.toUTF8(key));
        return addHeader(HEADER_AUTHORIZATION, authAsBase64);
    }

    public T addAccept(String acceptedTypes) {
        return addHeader(HEADER_ACCEPT, acceptedTypes);
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Helper methods
    /////////////////////////////////////////////////////////////////////////
    */

    // Ugh, co-variance is not always easy...
    @SuppressWarnings("unchecked")
    protected T _this() {
        return (T) this;
    }

    protected StringBuilder appendUrlEncoded(StringBuilder result, String key, String value)
    {
        if (result == null) {
            result = new StringBuilder(100);
        } else {
            result.append('&');
        }
        urlEncoder.appendEncoded(result, key);
        result.append('=');
        urlEncoder.appendEncoded(result, value);
        return result;
    }

    protected Throwable peelExceptions(Throwable t)
    {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    protected void throwIfUnchecked(Throwable t)
    {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }
    }

    protected <R> R handleAsNingException(Throwable t)
    {
        t = peelExceptions(t);
        throwIfUnchecked(t);
        // IOExceptions are assumed to be for data transfer...
        if (t instanceof IOException) {
            throw new NingTransferException((IOException) t);
        }
        throw new NingProcessingException(t);
    }
}
