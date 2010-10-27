package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.joda.time.ReadableDateTime;

/**
 * "Template" class used to indicate which fields should be fetched when retrieving
 * User items.
 * Uses fluent technique so that instances are immutable and new instance is created
 * for each "setter" method
 */
public enum UserField implements Typed
{
    // Id field(s):
    id(Key.class),

    // set of "minimal" fields
    author(String.class),
    createdDate(ReadableDateTime.class),

    // modifiable ones:
    approved(Boolean.class),
    statusMessage(String.class),
    
    // And then the rest

    updatedDate(ReadableDateTime.class),
    // ugh: API does spell it like this:
    email(String.class),
    url(URI.class), // profile URL
    fullName(String.class),
    gender(String.class),
    birthDate(ReadableDateTime.class, "birthdate"),
    iconUrl(URI.class),
    visibility(Visibility.class),
    commentCount(Integer.class),
    state(String.class),
    isOwner(Boolean.class),
    isAdmin(Boolean.class),
    isMember(Boolean.class),
    isBlocked(Boolean.class),
    location(String.class),
    profileQuestions(List.class) // how to pass generic type info?
    ;

    private final String external;
    private final Class<?> type;
    
    private UserField(Class<?> type) {
        this(type, null);
    }

    private UserField(Class<?> type, String external)
    {
        this.type = type;
        this.external = external;
    }

    public Class<?> type() { return type; }

    @Override public String toString() {
        return (external == null) ? name() : external;
    }
}
