package com.ning.api.client.json;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * POJO used for data binding responses that contain sequences of items,
 * just as most recent Blog Posts, or alphabetically ordered list of
 * users of a network.
 */
public class ItemSequenceResponse<T> extends ResponseWithResources
{
    private List<T> entry;

    private String anchorString;

    @JsonProperty private Boolean firstPage;
    @JsonProperty private Boolean lastPage;
    
    public ItemSequenceResponse() { }

    public List<T> getEntry()
    {
        // for convenience let's ensure it's never null
        if (entry == null) {
            return Collections.emptyList();
        }
        return entry;
    }

    /* due to a minor bug in precedence handling (in Jackson, before version 1.5.5 or so),
     * must define a setter here:
     */
    @SuppressWarnings("unused")
    @JsonProperty
    private void setEntry(List<T> entry) { this.entry = entry; }
    
    // ignore so it won't be serialized
    @JsonIgnore
    public boolean isEmpty() { return (entry == null) || entry.size() == 0; }

    @JsonIgnore
    public boolean isFirstPage() { return firstPage; }

    @JsonIgnore
    public boolean isLastPage() { return lastPage; }
    
    public String getAnchor() { return anchorString; }
    public void setAnchor(String str) { this.anchorString = str; }
}
