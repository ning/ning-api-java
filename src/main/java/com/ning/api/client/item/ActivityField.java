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
    attachedToAuthor(Key.class),

    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl"),

    attachedToAuthor_fullName(String.class, "attachedToAuthor.fullName"),
    attachedToAuthor_url(String.class, "attachedToAuthor.url"),
    attachedToAuthor_iconUrl(String.class, "attachedToAuthor.iconUrl"),

    image_url(String.class, "image.url"),
    image_width(Double.class, "image.width"),
    image_height(Double.class, "image.height"),
    
    ;

    private final Class<?> valueType;
    private final String external;
    
    private ActivityField(Class<?> type) {
        this(type, null);
    }

    private ActivityField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
