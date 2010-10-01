package com.ning.api.client.action;

import java.util.Collection;
import java.util.List;

import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Key;
import com.ning.api.client.sample.NingClientException;

/**
 * Simple common API for all types of counter objects; does not
 * include per-content-type configuration methods.
 */
public interface Finder<C extends ContentItem<?,C>>
{
    public C find(String id) throws NingClientException;
    public List<C> find(Collection<String> ids) throws NingClientException;
    public List<C> find(String[] ids) throws NingClientException;
    public C find(Key<C> id) throws NingClientException;
    public List<C> find(Key<C>[] ids) throws NingClientException;
}
