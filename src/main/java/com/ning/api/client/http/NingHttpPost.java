package com.ning.api.client.http;

//import com.ning.api.client.auth.OAuthSignatureCalculator;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

public class NingHttpPost
    extends NingHttpRequestWithBody<NingHttpPost>
{
    public NingHttpPost(BoundRequestBuilder rawRequest) //, OAuthSignatureCalculator sig)
    {
        super(rawRequest); //, sig);
    }

    @Override
    protected NingHttpPost _this() {
        return this;
    }
}
