package com.ning.api.client.item;

import java.net.URI;

import org.joda.time.ReadableDateTime;

public enum NetworkField  implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    subdomain(String.class),
    name(XapiStatus.class),
    xapiStatus(String.class), // enumerated, really
    iconUrl(URI.class),
    defaultUserIconUrl(URI.class),

    blogPostModeration(Boolean.class),
    eventModeration(Boolean.class),
    groupModeration(Boolean.class),
    photoModeration(Boolean.class),
    userModeration(Boolean.class),
    videoModeration(Boolean.class),

    // // Sub-resources -- not super elegant, but has to do for now
    author_fullName(String.class, "author.fullName"),
    author_url(String.class, "author.url"),
    author_iconUrl(String.class, "author.iconUrl"),
    ;

    private final Class<?> valueType;
    private final String external;
    
    private NetworkField(Class<?> type) {
        this(type, null);
    }

    private NetworkField(Class<?> type, String external)
    {
        this.valueType = type;
        this.external = external;
    }

    public Class<?> type() { return valueType; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
