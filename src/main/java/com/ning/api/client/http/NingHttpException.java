package com.ning.api.client.http;

import java.io.IOException;

import com.ning.api.client.NingClientException;

public class NingHttpException extends NingClientException
{
    private static final long serialVersionUID = 1L; // since Eclipse insists...

    /**
     * HTTP response code (usually error code) that caused this exception
     * to be thrown
     */
    protected final int httpResponseCode;

    protected final String httpResponseMessage;
    
    public NingHttpException(int httpResponseCode, String httpResponseMessage) {
        this(httpResponseCode, httpResponseMessage, "HTTP error "+httpResponseCode);
    }

    public NingHttpException(int httpResponseCode, String httpResponseMessage, String msg) {
        super(msg);
        this.httpResponseCode = httpResponseCode;
        this.httpResponseMessage = httpResponseMessage;
    }

    public NingHttpException(IOException e)
    {
        super(e);
        httpResponseMessage = null;
        httpResponseCode = 0; // unknown
    }
 
    public boolean hasHttpResponseCode() {
        return httpResponseCode > 0;
    }
    
    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public String getResponseMessage() { 
        return httpResponseMessage;
    }

    @Override
    public String toString() {
        return ""+getMessage()+"; response = '"+getResponseMessage()+"'";
    }
    
    @Override
    public boolean isRetryable() {
        // Everything with response code should be retryable, except for 4xx (client error)
        return hasHttpResponseCode() && (httpResponseCode < 400 || httpResponseCode >= 500);
    }
}
