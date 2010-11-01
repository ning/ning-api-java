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
    @JsonProperty protected String attachedToAuthor;

    public Activity() { }
    
    public String getType() { return type; }
    public Key<ContentItem<?,?>> getContentId() { return contentId; }
    public URI getUrl() { return url; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Key<ContentItem<?,?>> getAttachedTo() { return attachedTo; }
    public String getAttachedToType() { return attachedToType; }
    public String getAttachedToAuthor() { return attachedToAuthor; }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors, sub-resources (other than Author)
    ///////////////////////////////////////////////////////////////////////
     */
    
    /**
     * Method for getting Author object that represents author of the item
     * that this activity is associated with. To get non-null response
     * request must be specified that at least one of relevant properties
     * is fetched.
     */
    public Author getAttachedToAuthorResource()
    {
        if (attachedToAuthor == null) {
            return null;
        }
        return stdGetAuthorResource(attachedToAuthor);
    }

    /**
     * Method for getting Image object that represents image item that this
     * activity is associated with, if any (only some activity types do).
     */
    public Image getImageResource()
    {
        if (attachedTo == null) {
            return null;
        }
        return stdGetImageResource(attachedTo.toString());
    }
}
