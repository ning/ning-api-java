package com.ning.api.client.http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.SignatureCalculator;

/**
 * Wrapper around {@link AsyncHttpClient}, to add more convenience method for
 * constructing messages
 */
public class NingHttpClient
{
    private final AsyncHttpClient httpClient;

    public NingHttpClient() {
        this(null);
    }

    public NingHttpClient(SignatureCalculator sig)
    {
        httpClient = new AsyncHttpClient();
        if (sig != null) {
            httpClient.setSignatureCalculator(sig);
        }
    }

    public NingHttpDelete prepareDelete(String url, SignatureCalculator sig) {
        return new NingHttpDelete(httpClient.prepareDelete(url).setSignatureCalculator(sig));
    }
    
    public NingHttpGet prepareGet(String url, SignatureCalculator sig) {
        return new NingHttpGet(httpClient.prepareGet(url).setSignatureCalculator(sig));
    }
    
    public NingHttpPost preparePost(String url, SignatureCalculator sig) {
        return new NingHttpPost(httpClient.preparePost(url).setSignatureCalculator(sig));
    }

    public NingHttpPut preparePut(String url, SignatureCalculator sig) {
        return new NingHttpPut(httpClient.preparePut(url).setSignatureCalculator(sig));
    }
}
