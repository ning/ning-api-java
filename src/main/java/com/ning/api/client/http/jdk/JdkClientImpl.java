package com.ning.api.client.http.jdk;

import com.ning.api.client.http.NingHttpClient;
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpPut;

import com.ning.api.client.auth.OAuthSignatureCalculator;

/**
 * Implementation of {@link NingHttpClient} that uses Ning async HTTP client
 * for HTTP connectivity
 */
public class JdkClientImpl extends NingHttpClient
{
    public JdkClientImpl() { }

    public void close() {
        // anything we could do here? Probably not...
    }
    
    public NingHttpDelete prepareDelete(String url, OAuthSignatureCalculator sig) {
        return new NingHttpDelete(new JdkRequestBuilderImpl(url, sig, "DELETE"));
    }
    
    public NingHttpGet prepareGet(String url, OAuthSignatureCalculator sig) {
        return new NingHttpGet(new JdkRequestBuilderImpl(url, sig, "GET"));
    }
    
    public NingHttpPost preparePost(String url, OAuthSignatureCalculator sig) {
        return new NingHttpPost(new JdkRequestBuilderImpl(url, sig, "POST"));
    }

    public NingHttpPut preparePut(String url, OAuthSignatureCalculator sig) {
        return new NingHttpPut(new JdkRequestBuilderImpl(url, sig, "PUT"));
    }
}
