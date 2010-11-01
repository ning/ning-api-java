package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;

import org.joda.time.DateTime;

/**
 * Note: although we do implement {@link ContentItem}
 */
public class ContentItemBase<F extends Enum<F>, T extends ContentItem<F,T>>
    implements ContentItem<F,T>
{
    /*
    ///////////////////////////////////////////////////////////////////////
    // Directly deserialized properties
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Id of this content item; should never be null when returned via read-only
     * methods; null when constructed from scratch to create a new item
     */
    @JsonProperty
    protected Key<T> id;
    
    @JsonProperty
    protected DateTime createdDate;

    // looks like "author" is ubiquitous as well:
    @JsonProperty
    protected String author;

    /*
    ////////////////////////////////////    
///////////////////////////////////
    // Additional state
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Sub-resources that are injected by deserializer; may need to be
     * accessed for things like author information.
     */
    protected transient SubResources subResources;
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Life-cycle
    ///////////////////////////////////////////////////////////////////////
     */
    
    protected ContentItemBase() { }

    public void injectSubResources(SubResources sr) {
        subResources = sr;
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors, primary properties
    ///////////////////////////////////////////////////////////////////////
     */
    
    @JsonProperty("id")
    public final Key<T> id() { return id; }

    public DateTime getCreatedDate() { return createdDate; }

    /**
     * Accessor for accessing id (string that can be used to fetch User item that 
     * represents author) of author that created this item.
     */
    public String getAuthor() { return author; }
    
    @Override
    public String toString()
    {
        return "[Content type item of type "+getClass().getSimpleName()+"; id "+id+"]";
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors, sub-resources
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Accessor for accessing Author object constructed from "sub-resource" fields
     * (which MUST be requested explicitly as fields!) of this item.
     */
    public Author getAuthorResource() {
        return stdGetAuthorResource(author);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Helper methods for sub-classes
    ///////////////////////////////////////////////////////////////////////
     */

    protected Author stdGetAuthorResource(String authorId)
    {
        Author result = new Author(authorId);
        return subResources.findOrLoadResource(Author.class, authorId, result);
    }

    protected Image stdGetImageResource(String imageId)
    {
        return subResources.findOrLoadResource(Image.class, imageId);
    }

}
