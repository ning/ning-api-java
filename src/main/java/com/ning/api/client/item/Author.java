package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Value class used to represent "author sub-resource" -- piece of data associated with
 * primary items that can be requested along with main data for all content types.
 */
public class Author
{
    protected final String id;
    
    @JsonProperty protected URI url;
    @JsonProperty protected String fullName;
    @JsonProperty protected URI iconUrl;

    public Author(String id) {
        this(id, null, null, null);
    }

    public Author(String id, String fullName, URI url, URI iconUrl)
    {
        this.id = id;
        this.fullName = fullName;
        this.url = url;
        this.iconUrl = iconUrl;
    }
    
    public String getId() { return id; }
    public URI getUrl() { return url; }
    public String getFullName() { return fullName; }
    public URI getIconUrl() { return iconUrl; }
}
