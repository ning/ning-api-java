package com.ning.api.client.json;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @param <T> Type of item contained in response; usually a subtype of
 * {@link com.ning.api.client.item.ContentItem}
 */
public class ItemResponse<T> extends ResponseWithResources
{
    @JsonProperty
    private T entry;
    
    public ItemResponse() { }

    public T getEntry() { return entry; }
}
