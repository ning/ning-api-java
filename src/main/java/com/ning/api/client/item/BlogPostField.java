package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.joda.time.ReadableDateTime;

/**
 * "Template" class used to indicate which fields should be fetched when retrieving
 * BlogPost items.
 * Uses fluent technique so that instances are immutable and new instance is created
 * for each "setter" method
 */
public enum BlogPostField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then PUT/POSTable fields:
    description(String.class),
    publishTime(ReadableDateTime.class),
    publishStatus(PublishStatus.class),
    title(String.class),
    approved(Boolean.class), // note: can't use with POSTs?
    
    // And then the rest
    updatedDate(ReadableDateTime.class),
    url(URI.class),
    visibility(Visibility.class),
    commentCount(Integer.class),
    tags(List.class),
    birthDate(ReadableDateTime.class),
    email(String.class),

    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl"),
    
    ;

    private final Class<?> valueType;
    private final String external;
    
    private BlogPostField(Class<?> type) {
        this(type, null);
    }

    private BlogPostField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
