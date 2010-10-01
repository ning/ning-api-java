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

    // sub-properties?
    //   author.xxx, image.xxx
    ;

    private final Class<?> valueType;
    
    private NetworkField(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> type() { return valueType; }
}
