package com.ning.api.client.http.jdk;

import com.ning.api.client.http.NingHttpClient;
/*
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpPut;
import com.ning.http.client.AsyncHttpClient;
*/
import com.ning.http.client.SignatureCalculator;

/**
 * Implementation of {@link NingHttpClient} that uses Ning async HTTP client
 * for HTTP connectivity
 */
public abstract class JdkClientImpl extends NingHttpClient
{
    private final SignatureCalculator signatureCalculator;

    public JdkClientImpl() {
        this(null);
    }

    public JdkClientImpl(SignatureCalculator sig)
    {
        this.signatureCalculator = sig;
    }

    /*
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

*/
}
