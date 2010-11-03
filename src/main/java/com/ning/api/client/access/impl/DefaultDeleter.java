package com.ning.api.client.access.impl;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpException;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Key;

public class DefaultDeleter<C extends ContentItem<?,C>>
    implements Deleter<C>
{
    protected final NingConnection connection;

    protected NingClientConfig config;
    
    /**
     * Request end point used for fetching items
     */
    protected final String endpoint;

    protected final Key<C> id;

    public DefaultDeleter(NingConnection connection, NingClientConfig config, String endpoint,
            String id)
    {
        this(connection, config, endpoint, new Key<C>(id));
    }
    
    public DefaultDeleter(NingConnection connection, NingClientConfig config, String endpoint,
            Key<C> id)
    {
        this.connection = connection;
        this.config = config;
        this.endpoint = endpoint;
        this.id = id;
    }

    public void delete() throws NingHttpException
    {
        NingHttpDelete deleter = buildRequest(id);
        // should we wait big longer for successful modification?
        NingHttpResponse  response = deleter.execute(config.getWriteTimeoutMsecs());
        response.verifyResponse();
    }

    protected NingHttpDelete buildRequest(Key<C> id)
    {
        NingHttpDelete deleter = connection.prepareHttpDelete(endpoint, config);
        deleter = deleter.addAccept("*/*");
        deleter = deleter.addQueryParameter("id", id.toString());
        return deleter;
    }
}
