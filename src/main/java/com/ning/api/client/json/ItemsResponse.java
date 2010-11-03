package com.ning.api.client.json;

import java.util.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Response type used for "multi-get" requests; includes set of result items
 * along with possible sub-resources.
 * 
 * @param <T> Type of items contained in response; usually a subtype of
 * {@link com.ning.api.client.item.ContentItem}
 */
public class ItemsResponse<T> extends ResponseWithResources
{
    @JsonProperty
    private List<T> entry;
    
    public ItemsResponse() { }

    public List<T> getEntry()
    {
        // for convenience let's ensure it's never null
        if (entry == null) {
            return Collections.emptyList();
        }
        return entry;
    }

    // ignore so it won't be serialized
    @JsonIgnore
    public boolean isEmpty() { return (entry == null) || entry.isEmpty(); }
}
