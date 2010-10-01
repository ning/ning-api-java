package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.joda.time.ReadableDateTime;

/**
 * "Template" class used to indicate which fields should be fetched when retrieving
 * BlogPost items.
 * Uses fluent technique so that instances are immutable and new instance is created
 * for each "setter" method
 */
public enum BlogPostField implements Typed
{
    // First id/automatically included standard fields:
    id(Key.class),
    author(String.class),
    createdDate(ReadableDateTime.class),

    // then PUT/POSTable fields:
    description(String.class),
    publishTime(ReadableDateTime.class),
    publishStatus(PublishStatus.class),
    title(String.class),
    approved(Boolean.class), // note: can't use with POSTs?
    
    // And then the rest
    updatedDate(ReadableDateTime.class),
    url(URI.class),
    visibility(Visibility.class),
    commentCount(Integer.class),
    tags(List.class),
    birthDate(ReadableDateTime.class),
    email(String.class),
    ;

    private final Class<?> valueType;
    
    private BlogPostField(Class<?> type) {
        this.valueType = type;
    }

    public Class<?> type() { return valueType; }
}
