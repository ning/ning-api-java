package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;

import org.joda.time.ReadableDateTime;

public class Comment extends ContentItemBase<CommentField, Comment>
{
    @JsonProperty protected ReadableDateTime updatedDate;
    protected String description;

    @JsonProperty protected Key<? extends ContentItem<?,?>> attachedTo;
    @JsonProperty protected String attachedToType;
    @JsonProperty protected Key<User> attachedToAuthor;

    // note: non-public since it's only to be called by deserializer:
    protected Comment() { }

    /**
     * Constructor used for purpose of creating new comments.
     */
    public Comment(Key<? extends ContentItem<?,?>> attachedTo, String description)
    {
        this.attachedTo = attachedTo;
        this.description = description;
    }
    
    // Mutable properties:
    
    public String getDescription() { return description; }
    public void setDescription(String s) { description = s; }

    // Others:

    public ReadableDateTime getUpdatedDate() { return updatedDate; }
    public Key<? extends ContentItem<?,?>> getAttachedTo() { return attachedTo; }
    public String getAttachedToType() { return attachedToType; }
    public Key<User> getAttachedToAuthor() { return attachedToAuthor; }
}
