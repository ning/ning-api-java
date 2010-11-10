package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.ReadableDateTime;

public class Category extends ContentItemBase<CategoryField, Category>
{
    // Read/write properties:

    protected String description;
    protected String title;
    protected Visibility visibility;

    // Read-only properties:
    @JsonProperty protected ReadableDateTime updatedDate;

    /**
     * Link to browser-viewable representation of the item
     */
    @JsonProperty protected URI url;
    @JsonProperty protected List<String> tags;

    protected Boolean isClosed;
    protected Boolean isPrivate;
    
    public Category() { this(null); }
    public Category(Key<Category> id) { this.id = id; }

    // Read/write properties:
    
    public String getDescription() { return description; }
    public void setDescription(String s) { description = s; }

    public String getTitle() { return title; }
    public void setTitle(String s) { title = s; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility v) { visibility = v; }
    
    // Read-only properties

    public Boolean isClosed() { return isClosed; }
    public Boolean isPrivate() { return isPrivate; }
    public URI getUrl() { return url; }
    public List<String> getTags() { return tags; }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors, sub-resources (other than Author)
    ///////////////////////////////////////////////////////////////////////
     */
    
}
