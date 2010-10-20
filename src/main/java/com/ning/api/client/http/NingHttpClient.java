package com.ning.api.client.http;

import com.ning.api.client.auth.OAuthSignatureCalculator;

/**
 * Wrapper around {@link AsyncClientImpl}, to add more convenience method for
 * constructing messages
 */
public abstract class NingHttpClient
{
    public abstract NingHttpDelete prepareDelete(String url, OAuthSignatureCalculator sig);
    
    public abstract NingHttpGet prepareGet(String url, OAuthSignatureCalculator sig);
    
    public abstract NingHttpPost preparePost(String url, OAuthSignatureCalculator sig);

    public abstract NingHttpPut preparePut(String url, OAuthSignatureCalculator sig);
}
