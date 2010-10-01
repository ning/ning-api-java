package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public class Key<T>
{
    private final static Key<Object> EMPTY = new Key<Object>(null);
    
    private final String stringValue;

    @JsonCreator // optional, would be auto-detected
    public Key(String stringValue)
    {
        this.stringValue = (stringValue == null || stringValue.isEmpty()) ? null : stringValue;
    }

    @SuppressWarnings("unchecked")
    public static <T> Key<T> emptyKey() {
        return (Key<T>) EMPTY;
    }

    public boolean isEmpty() {
        return (stringValue == null);
    }

    public int length() {
        return stringValue == null ? 0 : stringValue.length();
    }

    @JsonValue
    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key<?> key = (Key<?>) o;
        return stringValue.equals(key.stringValue);
    }

    @Override
    public int hashCode() {
        return stringValue.hashCode();
    }
}
