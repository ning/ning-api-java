package com.ning.api.client.access.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ning.api.client.NingClientException;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.Finder;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Key;
import com.ning.api.client.item.Typed;

/**
 * Standard implementation for typical Finders.
 * 
 * @author tatu
 *
 * @param <C> Type of content item(s) to find
 * @param <F> Enumeration of fields for T
 */
public class DefaultFinder<
    C extends ContentItem<F, C>,
    F extends Enum<F> & Typed
>
    implements Finder<C>
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
    
    /**
     * Fields to fetch values for, for items listed
     */
    protected final Fields<F> fields;

    protected final Class<C> itemType;
    
    public DefaultFinder(NingConnection connection, long timeoutMsecs, String endpoint,
            Class<C> itemType, Fields<F> fields)
    {
        this.connection = connection;
        this.timeoutMsecs = timeoutMsecs;
        this.endpoint = endpoint;
        this.fields = fields;
        this.itemType = itemType;
    }

    public final C find(String id) throws NingClientException
    {
        return find(new Key<C>(id));
    }

    public final List<C> find(Collection<String> ids) throws NingClientException
    {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        @SuppressWarnings("unchecked")
        Key<C>[] keys = (Key<C>[]) new Key<?>[ids.size()];
        int i =  0;
        for (String id : ids) {
            keys[i++] = new Key<C>(id);
        }
        return find(keys);
    }
    
    public final List<C> find(String[] ids) throws NingClientException
    {
        @SuppressWarnings("unchecked")
        Key<C>[] keys = (Key<C>[]) new Key<?>[ids.length];
        for (int i = 0, len = ids.length; i < len; ++i) {
            keys[i] = new Key<C>(ids[i]);
        }
        return find(keys);
    }
    
    public C find(Key<C> id)
    {
        NingHttpGet getter = buildQuery(id);
        return getter.execute(timeoutMsecs).asSingleItem(itemType);
    }

    public List<C> find(Key<C>[] ids) {
        // !!! TODO
        return null;
    }

    protected NingHttpGet buildQuery(Key<C> id)
    {
        NingHttpGet getter = prepareQuery();
        getter = getter.addQueryParameter("id", id.toString());
        return getter;
    }

    /**
     * Overridable method sub-classes can define to add other query parameters
     */
    protected NingHttpGet prepareQuery()
    {
        NingHttpGet getter = connection.prepareHttpGet(endpoint);
        getter = getter.addAccept("* /*");
        // also, need to specify fields to include with "fields"
        if (fields != null && !fields.isEmpty()) {
            getter = getter.addQueryParameter("fields", fields.toString());
        }
        return getter;
    }
}

