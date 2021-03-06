package com.ning.api.client.action;

import com.ning.api.client.NingClientException;
import com.ning.api.client.item.ContentItem;

public interface Updater<C extends ContentItem<?,C>>
{
    /**
     * Method to call to update an existing item, based on configuration of this
     * updater instance
     * 
     * @throws NingClientException If request fails for some reason
     */
    public void update() throws NingClientException;
}
