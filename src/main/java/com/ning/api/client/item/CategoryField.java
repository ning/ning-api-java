package com.ning.api.client.item;

import java.net.URI;

import org.joda.time.ReadableDateTime;

public enum CategoryField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then PUT/POSTable fields:
    title(String.class),
    description(String.class),
    visibility(Visibility.class),

    // And then the rest
    isPrivate(Boolean.class, "private"),    
    updatedDate(ReadableDateTime.class),
    url(URI.class),
    
    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl")    
    ;

    private final Class<?> valueType;
    private final String external;
    
    private CategoryField(Class<?> type) {
        this(type, null);
    }

    private CategoryField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
