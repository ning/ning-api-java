package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.ReadableDateTime;

import java.util.*;

public class Photo
    extends ContentItemBase<PhotoField, Photo>
    implements Cloneable
{
    // Read/write properties:

    protected String description;
    protected String title;
    protected Visibility visibility;

    protected Boolean approved;

    // Read-only properties:
    @JsonProperty protected ReadableDateTime updatedDate;
    /**
     * Link to browser-viewable representation of the item
     */
    @JsonProperty protected URI url;
    @JsonProperty protected Integer commentCount;
    @JsonProperty protected List<String> tags;
    
    public Photo() { this(null); }
    public Photo(Key<Photo> id) { this.id = id; }

    /**
     * Let's expose clone() for convenient immutable/fluent style pattern by
     * builders
     */
    public Photo clone() {
        try {
            return (Photo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    // Read/write properties:
    
    public String getDescription() { return description; }
    public void setDescription(String s) { description = s; }

    public String getTitle() { return title; }
    public void setTitle(String s) { title = s; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility v) { visibility = v; }
    
    public Boolean isApproved() { return approved; }
    public void setApproved(Boolean b) { approved = b; }
    
    
    // Read-only properties

    public URI getUrl() { return url; }
    public Integer getCommentCount() { return commentCount; }
    public List<String> getTags() { return tags; }
}
