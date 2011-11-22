package com.ning.api.client.item;

import org.joda.time.ReadableDateTime;

public enum FriendField implements Typed {
    // First id/automatically included standard fields:
    friend(String.class),

    // And then the rest
    author(String.class),
    state(FriendState.class),
    createdDate(ReadableDateTime.class),
    updatedDate(ReadableDateTime.class)
    ;

    private final Class<?> valueType;
    private final String external;

    private FriendField(Class<?> type) {
        this(type, null);
    }

    private FriendField(Class<?> type, String external) {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() {
        return valueType;
    }

    @Override
    public String toString() {
        return (external == null) ? name() : external;
    }
}
