package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.ReadableDateTime;

public class Topic
    extends ContentItemBase<TopicField, Topic>
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

    @JsonProperty protected String image;
    
    public Topic() { this(null); }
    public Topic(Key<Topic> id) { this.id = id; }

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

    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors, sub-resources (other than Author)
    ///////////////////////////////////////////////////////////////////////
     */
    
}
