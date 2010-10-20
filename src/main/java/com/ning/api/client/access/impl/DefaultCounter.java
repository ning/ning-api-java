package com.ning.api.client.access.impl;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.NingClientException;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.Counter;
import com.ning.api.client.exception.NingTransformException;
import com.ning.api.client.http.NingHttpGet;

/**
 * Standard implementation for typical Finders.
 */
public abstract class DefaultCounter
    implements Counter
{
    protected final NingConnection connection;

    /**
     * Timeout to use for calls
     */
    protected final NingClientConfig config;

    /**
     * Request end point used for fetching items
     */
    protected final String endpoint;

    /**
     * Basic filter that determines timepoint after which (inclusive?) items
     * are to be included.
     */
    protected final ReadableDateTime createdAfter;

    /**
     * Optional filter for only counting items authored by specified
     * user.
     */
    protected final String author;

    protected final Boolean isPrivate;

    protected final Boolean isApproved;
    
    public DefaultCounter(NingConnection connection, NingClientConfig config, String endpoint,
            ReadableDateTime createdAfter, String author,
            Boolean isPrivate, Boolean isApproved)
    {
        this.connection = connection;
        this.config = config;
        this.endpoint = endpoint;
        
        this.createdAfter = createdAfter;

        this.author = author;
        this.isPrivate = isPrivate;
        this.isApproved = isApproved;
    }

    //@Override
    public int count() throws NingClientException
    {
        NingHttpGet getter = buildQuery();
        Integer count = getter.execute(config.getReadTimeoutMsecs()).asCount();
        if (count == null) { // should never occur, but:
            throw new NingTransformException("Response did not contain 'count' property");
        }
        return count.intValue();
    }
    
    protected NingHttpGet buildQuery()
    {
        NingHttpGet getter = connection.prepareHttpGet(endpoint);
        getter = getter.addAccept("*/*");
        // always must use 'createdAfter' filter":
        getter = getter.addQueryParameter("createdAfter", createdAfter.toString());
        // but other pieces are optional
        if (author != null && author.length() > 0) {
            getter = getter.addQueryParameter("author", author);
        }
        if (isPrivate != null) {
            getter = getter.addQueryParameter("private", isPrivate.toString());
        }
        if (isApproved != null) {
            getter = getter.addQueryParameter("approved", isApproved.toString());
        }
        return getter;
    }
}
