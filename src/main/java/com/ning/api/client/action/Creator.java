package com.ning.api.client.action;

import com.ning.api.client.NingClientException;
import com.ning.api.client.item.ContentItem;

/**
 * Common parts of API for type-specific creators.
 */
public interface Creator<C extends ContentItem<?,C>>
{
    /**
     * Method to call to create a new item, based on configuration of this
     * creator instance
     * 
     * @throws NingClientException If request fails for some reason
     */
    public void create() throws NingClientException;
}
