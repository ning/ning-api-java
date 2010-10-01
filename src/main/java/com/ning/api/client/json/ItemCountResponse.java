package com.ning.api.client.json;

import org.codehaus.jackson.annotate.JsonProperty;

public class ItemCountResponse extends XapiResponse
{
    @JsonProperty
    private Integer count;
    
    public ItemCountResponse() { }

    public boolean hasCount() { return count != null; }
    public Integer getCount() { return count; }
}
