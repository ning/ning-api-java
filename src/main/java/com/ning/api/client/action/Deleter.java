package com.ning.api.client.action;

import com.ning.api.client.item.ContentItem;
import com.ning.api.client.sample.NingClientException;

/**
 * Simple common API for deleters for all types of content items.
 */
public interface Deleter<C extends ContentItem<?, C>>
{
    public void delete() throws NingClientException;
}
