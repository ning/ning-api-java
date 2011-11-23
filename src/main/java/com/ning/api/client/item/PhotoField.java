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
    topTags(List.class),

    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl"),

    image_url(String.class, "image.url"),
    image_width(Double.class, "image.width"),
    image_height(Double.class, "image.height"),
    ;

    private final Class<?> valueType;
    private final String external;

    private PhotoField(Class<?> type) {
        this(type, null);
    }

    private PhotoField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
