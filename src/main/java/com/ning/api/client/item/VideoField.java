package com.ning.api.client.item;

import org.joda.time.ReadableDateTime;

public enum VideoField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then PUT/POSTable fields:
    description(String.class),
    title(String.class),
    
    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl")
    
    ;

    private final Class<?> valueType;
    private final String external;
    
    private VideoField(Class<?> type) {
        this(type, null);
    }

    private VideoField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
