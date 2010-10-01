package com.ning.api.client.json;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.item.ContentItem;

/**
 * Intermediate response type class for responses that can contain
 * associated "sub-resources". This currently contains all response types
 * except simple count query responses.
 */
public class ResponseWithResources extends XapiResponse
{
    @JsonProperty
    private LinkedHashMap<String,JsonNode> resources;

    public ResponseWithResources() { }

    /* TODO: should this be exposed?
    public LinkedHashMap<String,JsonNode> getResources()
    {
        return resources;
    }
*/

    public <T extends ContentItem<?, T>> T getResource(String key, Class<T> type,
            ObjectMapper mapper)
        throws IOException
    {
        if (resources != null) {
            JsonNode node = resources.get(key);
            if (node != null) {
                return mapper.readValue(node.traverse(), type);
            }
        }
        return null;
    }
}
