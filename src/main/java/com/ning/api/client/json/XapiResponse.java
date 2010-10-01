package com.ning.api.client.json;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Base class for all response types: the only commonality is the
 * response code, which is contained here.
 */
public class XapiResponse
{
    @JsonProperty
    private String success;
    
    protected XapiResponse() { }

    public String getSuccess() { return success; }
}
