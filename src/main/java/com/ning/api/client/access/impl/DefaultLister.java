package com.ning.api.client.access.impl;

import java.util.*;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.impl.ItemIteratorImpl;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Typed;

/**
 * Base class for listers, builder objects used for building
 * and executing list (/alpha, /recent) requests.
 */
public abstract class DefaultLister<
    C extends ContentItem<F, C>,
    F extends Enum<F> & Typed
>
    implements Iterable<C>
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
     * Fields to fetch values for, for items listed
     */
    protected final Fields<F> fields;

    /**
     * Optional filter for only counting items authored by specified
     * user.
     */
    protected final String author;

    protected final Boolean isPrivate;

    protected final Boolean isApproved;

    protected DefaultLister(NingConnection connection, NingClientConfig config, String endpoint,
            Fields<F> fields,
            String author, Boolean isPrivate, Boolean isApproved)
    {
        this.connection = connection;
        this.config = config;
        this.endpoint = endpoint;
        this.fields = fields;
        this.author = author;
        this.isPrivate = isPrivate;
        this.isApproved = isApproved;
    }

    /**
     * Method that will construct list request with current configuration
     * and build an iterator that will iterator through listed items,
     * making additional requests as necessary to fetch more items until
     * there are no more to iterate. Underlying iterator usually fetches
     * items in chunks, so each call does not necessarily translate to
     * request to server.
     */
    public Iterator<C> iterator() {
        return new ItemIteratorImpl<C>(list());
    }

    /**
     * Method that will construct accessor object that can be used to
     * access content in "paged" fashion; by fetching blocks of items
     * with each call (and one actual server request per call as well).
     */
    public abstract PagedList<C> list();
}
