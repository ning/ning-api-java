package com.ning.api.client.http;

public class NingHttpPost
    extends NingHttpRequestWithBody<NingHttpPost>
{
    public NingHttpPost(NingRequestBuilder<?> requestBuilder)
    {
        super(requestBuilder);
    }

    @Override
    protected NingHttpPost _this() {
        return this;
    }
}
