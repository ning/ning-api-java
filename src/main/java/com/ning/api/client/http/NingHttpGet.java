package com.ning.api.client.http;

//import com.ning.api.client.auth.OAuthSignatureCalculator;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

public class NingHttpGet
    extends NingHttpRequest<NingHttpGet>
{
    public NingHttpGet(BoundRequestBuilder rawRequest) // , OAuthSignatureCalculator sig)
    {
        super(rawRequest); //, sig);
    }
}
