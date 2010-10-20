package com.ning.api.client.http.async;

import com.ning.api.client.http.NingHttpClient;
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpPut;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.SignatureCalculator;

/**
 * Implementation of {@link NingHttpClient} that uses Ning async HTTP client
 * for HTTP connectivity
 */
public class AsyncClientImpl extends NingHttpClient
{
    private final AsyncHttpClient httpClient;

    public AsyncClientImpl() {
        this(null);
    }

    public AsyncClientImpl(SignatureCalculator sig)
    {
        httpClient = new AsyncHttpClient();
        if (sig != null) {
            httpClient.setSignatureCalculator(sig);
        }
    }

    public NingHttpDelete prepareDelete(String url, SignatureCalculator sig) {
        return new NingHttpDelete(new AsyncRequestBuilderImpl(httpClient.prepareDelete(url).setSignatureCalculator(sig)));
    }
    
    public NingHttpGet prepareGet(String url, SignatureCalculator sig) {
        return new NingHttpGet(new AsyncRequestBuilderImpl(httpClient.prepareGet(url).setSignatureCalculator(sig)));
    }
    
    public NingHttpPost preparePost(String url, SignatureCalculator sig) {
        return new NingHttpPost(new AsyncRequestBuilderImpl(httpClient.preparePost(url).setSignatureCalculator(sig)));
    }

    public NingHttpPut preparePut(String url, SignatureCalculator sig) {
        return new NingHttpPut(new AsyncRequestBuilderImpl(httpClient.preparePut(url).setSignatureCalculator(sig)));
    }
}
