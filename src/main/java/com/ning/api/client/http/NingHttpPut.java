package com.ning.api.client.http;

public class NingHttpPut
    extends NingHttpRequestWithBody<NingHttpPut>
{
    public NingHttpPut(NingRequestBuilder<?> requestBuilder)
    {
        super(requestBuilder);
    }

    @Override
    protected NingHttpPut _this() {
        return this;
    }
}
