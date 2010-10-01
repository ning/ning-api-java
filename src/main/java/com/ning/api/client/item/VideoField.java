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
    
    // And then the rest
    
    ;

    private final Class<?> type;
    
    private VideoField(Class<?> type) {
        this.type = type;
    }

    public Class<?> type() { return type; }
}
