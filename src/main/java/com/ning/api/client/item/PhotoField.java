package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.joda.time.ReadableDateTime;

public enum PhotoField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then PUT/POSTable fields:
    approved(Boolean.class), // note: can't use with POSTs?
    title(String.class),
    description(String.class),
    visibility(Visibility.class),
    
    // And then the rest
    updatedDate(ReadableDateTime.class),
    url(URI.class),
    commentCount(Integer.class),
    tags(List.class),

    // Sub-resources? author.xxx, image.xxx
    
    ;

    private final Class<?> valueType;
    
    private PhotoField(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> type() { return valueType; }

}
