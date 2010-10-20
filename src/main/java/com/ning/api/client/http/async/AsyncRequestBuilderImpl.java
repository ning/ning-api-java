package com.ning.api.client.http.async;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.http.NingRequestBuilder;
import com.ning.http.client.Response;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

public class AsyncRequestBuilderImpl extends NingRequestBuilder<AsyncRequestBuilderImpl>
{
    protected BoundRequestBuilder requestBuilder;

    public AsyncRequestBuilderImpl(BoundRequestBuilder requestBuilder)
    {
        this.requestBuilder = requestBuilder;
    }

    @Override
    public AsyncRequestBuilderImpl addHeader(String header, String value)
    {
        requestBuilder = requestBuilder.addHeader(header, value);
        return this;
    }

    @Override
    public AsyncRequestBuilderImpl addQueryParameter(String name, String value)
    {
        requestBuilder = requestBuilder.addQueryParameter(name, value);
        return this;
    }

    @Override
    public Future<NingHttpResponse> sendRequest(ObjectMapper objectMapper) throws IOException
    {
        return new ResponseFuture(objectMapper, requestBuilder.execute());
    }

    @Override
    public AsyncRequestBuilderImpl addFormParameter(String name, String value)
    {
        requestBuilder = requestBuilder.addParameter(name, value);
        return this;
    }

    @Override
    public AsyncRequestBuilderImpl setBody(String body) {
        requestBuilder = requestBuilder.setBody(body);
        return null;
    }

    /*
     */
    protected final static class ResponseFuture implements Future<NingHttpResponse>
    {
        protected final ObjectMapper objectMapper;
        
        protected final Future<Response> futureImpl;

        public ResponseFuture(ObjectMapper objectMapper, Future<Response> futureImpl)
        {
            this.objectMapper = objectMapper;
            this.futureImpl = futureImpl;
        }
        
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return futureImpl.cancel(mayInterruptIfRunning);
        }

        @Override
        public NingHttpResponse get() throws InterruptedException, ExecutionException {
            return new AsyncResponseImpl(objectMapper, futureImpl.get());
        }

        @Override
        public NingHttpResponse get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException
        {
            return new AsyncResponseImpl(objectMapper, futureImpl.get(timeout, unit));
        }

        @Override
        public boolean isCancelled() {
            return futureImpl.isCancelled();
        }

        @Override
        public boolean isDone() {
            return futureImpl.isDone();
        }
    }
}
