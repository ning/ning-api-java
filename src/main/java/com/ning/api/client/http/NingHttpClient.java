package com.ning.api.client.http;

import com.ning.http.client.SignatureCalculator;

/**
 * Wrapper around {@link AsyncClientImpl}, to add more convenience method for
 * constructing messages
 */
public abstract class NingHttpClient
{
    public abstract NingHttpDelete prepareDelete(String url, SignatureCalculator sig);
    
    public abstract NingHttpGet prepareGet(String url, SignatureCalculator sig);
    
    public abstract NingHttpPost preparePost(String url, SignatureCalculator sig);

    public abstract NingHttpPut preparePut(String url, SignatureCalculator sig);
}
