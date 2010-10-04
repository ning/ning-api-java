package com.ning.api.client.access.impl;

import java.util.*;

import com.ning.api.client.NingClientException;
import com.ning.api.client.access.Anchor;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpRequest.Param;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Typed;

public class PagedListImpl <C extends ContentItem<F, C>,
    F extends Enum<F> & Typed>
    implements PagedList<C>
{
    /**
     * Object we need for sending actual requests.
     */
    protected final NingConnection connection;

    /**
     * Timeout to use for calls
     */
    protected final long timeoutMsecs;
    
    /**
     * Endpoint to call; includes information about content type as well as
     * iteration axis (alphabetic, most-recent)
     */
    protected final String endpoint;

    protected final Class<C> itemClass;
    
    protected final Fields<F> fields;
    
    protected final String author;
    
    protected final Boolean isPrivate;

    protected final Boolean isApproved;
    
    protected final Param[] additionalQueryParams;
    
    /**
     * During iteration we need to keep track of position most recently
     * accessed
     */
    protected final AnchorHolder anchor;
    
    public PagedListImpl(NingConnection connection, long timeoutMsecs, String endpoint,
            Class<C> itemClass,  Fields<F> fields,
            String author, Boolean isPrivate, Boolean isApproved,
            Param... additionalQueryParams)
    {
        this.connection = connection;
        this.timeoutMsecs = timeoutMsecs;
        this.endpoint = endpoint;
        
        this.itemClass = itemClass;
        this.fields = fields;
        this.author = author;
        this.isPrivate = isPrivate;
        this.isApproved = isApproved;
        this.additionalQueryParams = additionalQueryParams;

        anchor = new AnchorHolder();
    }
    
    public List<C> next(int pageSize) {
        return fetchSequence(pageSize);
    }

    public List<C> previous(int pageSize) {
        // to go backwards, just negate sign
        return fetchSequence(-pageSize);
    }
    
    protected List<C> fetchSequence(int count)
        throws NingClientException
    {
        NingHttpGet getter = connection.prepareHttpGet(endpoint);
        getter = getter.addAccept("*/*");
        getter = getter.addQueryParameter("count", String.valueOf(count));
        // also, need to specify fields to include with "fields"
        getter = getter.addQueryParameter("fields", fields.toString());
        if (author != null) {
            getter = getter.addQueryParameter("author", author);
        }
        if (isPrivate != null) {
            getter = getter.addQueryParameter("private", isPrivate.toString());
        }
        if (isApproved != null) {
            getter = getter.addQueryParameter("approved", isApproved.toString());
        }
        if (anchor != null && anchor.hasAnchor()) {
            getter = getter.addQueryParameter("anchor", anchor.toString());
        }
        if (additionalQueryParams != null) {
            getter = getter.addQueryParameters(additionalQueryParams);
        }
        return getter.execute(timeoutMsecs).asItemList(itemClass, anchor);
    }

    public Anchor position() {
        return anchor.getAnchor();
    }

}
