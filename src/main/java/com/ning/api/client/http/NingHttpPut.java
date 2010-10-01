package com.ning.api.client.http;

//import com.ning.api.client.auth.OAuthSignatureCalculator;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

public class NingHttpPut
    extends NingHttpRequestWithBody<NingHttpPut>
{
    public NingHttpPut(BoundRequestBuilder rawRequest)
    {
        super(rawRequest);
    }

    @Override
    protected NingHttpPut _this() {
        return this;
    }
}
