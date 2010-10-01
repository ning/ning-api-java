package com.ning.api.client.item;

import org.joda.time.ReadableDateTime;

public enum CommentField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then others
    updatedDate(ReadableDateTime.class),
    description(String.class),
    attachedTo(Key.class),
    attachedToType(String.class),
    attachedToAuthor(Key.class)
    ;

    private final Class<?> valueType;
    
    private CommentField(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> type() { return valueType; }

}
