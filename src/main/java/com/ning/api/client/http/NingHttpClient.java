package com.ning.api.client.http;

import com.ning.api.client.auth.OAuthSignatureCalculator;

/**
 * Base class for implementations that wrap specific HTTP client libraries used
 * for communications.
 *<p>
 * Currently we support two libraries: Ning's async library (default); and JDK based
 * one that is used on more limited platforms (like Android).
 */
public abstract class NingHttpClient
{
    public abstract NingHttpDelete prepareDelete(String url, OAuthSignatureCalculator sig);
    
    public abstract NingHttpGet prepareGet(String url, OAuthSignatureCalculator sig);
    
    public abstract NingHttpPost preparePost(String url, OAuthSignatureCalculator sig);

    public abstract NingHttpPut preparePut(String url, OAuthSignatureCalculator sig);
}
