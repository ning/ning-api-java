package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

/**
 * Note: although we do implement {@link ContentItem}
 */
public class ContentItemBase<F extends Enum<F>, T extends ContentItem<F,T>>
    implements ContentItem<F,T>
{
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
    
    protected ContentItemBase() { }
    
    @JsonProperty("id")
    public final Key<T> id() { return id; }

    public DateTime getCreatedDate() { return createdDate; }
    public String getAuthor() { return author; }

    @Override
    public String toString()
    {
        return "[Content type item of type "+getClass().getSimpleName()+"; id "+id+"]";
    }

}
