package com.ning.api.client.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Intermediate response type class for responses that can contain
 * associated "sub-resources". This currently contains all response types
 * except simple count query responses.
 */
public class ResponseWithResources extends XapiResponse
{
    @JsonProperty
    protected JsonNode resources;

    public ResponseWithResources() { }

    public JsonNode getResources() { return resources; }
}
