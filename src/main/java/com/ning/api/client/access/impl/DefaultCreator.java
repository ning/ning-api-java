package com.ning.api.client.access.impl;

import com.ning.api.client.NingClientException;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.Creator;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.item.ContentItem;

public abstract class DefaultCreator<C extends ContentItem<?,C>> implements Creator<C>
{
    protected final NingConnection connection;

    /**
     * Timeout to use for calls
     */
    protected final long timeoutMsecs;

    /**
     * Request end point used for fetching items
     */
    protected final String endpoint;

    protected DefaultCreator(NingConnection connection, long timeoutMsecs, String endpoint)
    {
        this.connection = connection;
        this.timeoutMsecs = timeoutMsecs;
        this.endpoint = endpoint;
    }
    
    @Override
    public void create() throws NingClientException
    {
        NingHttpPost post = buildCreate();
        NingHttpResponse  response = post.execute(timeoutMsecs);
        response.verifyResponse();
    }

    protected NingHttpPost buildCreate()
    {
        NingHttpPost creator = connection.prepareHttpPost(endpoint);
        creator = creator.addAccept("*/*");
        creator = addCreateParameters(creator);
        return creator;
    }

    protected abstract NingHttpPost addCreateParameters(NingHttpPost create);
    
}
