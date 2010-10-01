package com.ning.api.client.action;

import java.util.*;

import com.ning.api.client.access.Anchor;
import com.ning.api.client.item.ContentItem;

/**
 * Accessor object for traversing sequences of items ("lists").
 * Unlike generic {@link java.util.Iterator} accessor (which is
 * the main alternative), this object makes chunking explicit,
 * such that each call will result in corresponding list
 * request being sent. It is also possible to go back and forth,
 * as well as use different chunk (page) sizes for access.
 */
public interface PagedList<C extends ContentItem<?, C>>
{
    public abstract List<C> next(int pageSize);

    public abstract List<C> previous(int pageSize);

    public abstract Anchor position();
}
