package com.ning.api.client.access.impl;

import java.util.*;

import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.ContentItem;

public class ItemIteratorImpl<C extends ContentItem<?, C>>
    implements Iterator<C>
{
    public final static int DEFAULT_CHUNK_SIZE = 20;
    
    protected final PagedList<C> pageLister;

    /**
     * Number of items we will try to fetch with each call
     */
    protected final int itemsPerCall;
    
    protected Iterator<C> currentItems;
    
    /**
     * Marker that is set when underlying iterator finishes
     */
    protected boolean closed;

    public ItemIteratorImpl(PagedList<C> pageLister) {
        this(pageLister, DEFAULT_CHUNK_SIZE);
    }
    
    public ItemIteratorImpl(PagedList<C> pageLister, int itemsPerCall)
    {
        this.pageLister = pageLister;
        this.itemsPerCall = itemsPerCall;
    }
    
    //@Override
    public boolean hasNext()
    {
        if (closed) return false;
        if (currentItems == null || !currentItems.hasNext()) {
            if (!fetchMore()) {
                return false;
            }
        }
        return true;
    }

    //@Override
    public C next()
    {
        if (currentItems == null || !currentItems.hasNext()) {
            if (closed || !fetchMore()) {
                throw new NoSuchElementException("No more entries to iterate");
            }
        }
        return currentItems.next();
    }

    //@Override
    public void remove() {
        throw new UnsupportedOperationException("Item lists are read-only");
    }

    protected boolean fetchMore() {
        List<C> items = pageLister.next(itemsPerCall);
        if (items.isEmpty()) {
            closed = true;
            return false;
        }
        currentItems = items.iterator();
        return true;
    }
}
