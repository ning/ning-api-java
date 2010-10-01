package com.ning.api.client.item;

import java.net.URI;

import org.joda.time.ReadableDateTime;

public enum ActivityField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    type(String.class),
    contentId(Key.class),
    url(URI.class),
    title(String.class),
    description(String.class),
    attachedTo(Key.class),
    attachedToType(String.class),
    attachedToAuthor(Key.class)

    // sub-properties?
    //   author.xxx, image.xxx
    ;

    private final Class<?> valueType;
    
    private ActivityField(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> type() { return valueType; }

}
