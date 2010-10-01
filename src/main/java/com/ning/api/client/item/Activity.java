package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;

public class Activity extends ContentItemBase<ActivityField, Activity>
{
    // Base class includes standard ones: id, author, createdDate
    

    @JsonProperty protected String type;
    @JsonProperty protected Key<ContentItem<?,?>> contentId;
    @JsonProperty protected URI url;
    @JsonProperty protected String title;
    @JsonProperty protected String description;

    @JsonProperty protected Key<ContentItem<?,?>> attachedTo;
    @JsonProperty protected String attachedToType;
    @JsonProperty protected Key<User> attachedToAuthor;

    public Activity() { }
    
    public String getType() { return type; }
    public Key<ContentItem<?,?>> getContentId() { return contentId; }
    public URI getUrl() { return url; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Key<ContentItem<?,?>> getAttachedTo() { return attachedTo; }
    public String getAttachedToType() { return attachedToType; }
    public Key<User> getAttachedToAuthor() { return attachedToAuthor; }
}
