package com.ning.api.client.access.impl;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.NingClientException;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.Updater;
import com.ning.api.client.http.NingHttpPut;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.item.ContentItem;

public abstract class DefaultUpdater <C extends ContentItem<?,C>> implements Updater<C>
{
    protected final NingConnection connection;

    /**
     * Timeout to use for calls
     */
    protected NingClientConfig config;

    /**
     * Request end point used for fetching items
     */
    protected final String endpoint;

    protected DefaultUpdater(NingConnection connection, NingClientConfig config, String endpoint)
    {
        this.connection = connection;
        this.config = config;
        this.endpoint = endpoint;
    }
    
    //@Override
    public void update() throws NingClientException
    {
        NingHttpPut put = buildUpdate();
        NingHttpResponse  response = put.execute(config.getWriteTimeoutMsecs());
        response.verifyResponse();
    }

    protected NingHttpPut buildUpdate()
    {
        NingHttpPut putter = connection.prepareHttpPut(endpoint);
        putter = putter.addAccept("*/*");
        putter = addUpdateParameters(putter);
        return putter;
    }

    protected abstract NingHttpPut addUpdateParameters(NingHttpPut put);
}
