package com.ning.api.client.item;

/**
 * Interface used to "decorate" enums with type information needed for data conversion
 * or binding
 */
public interface Typed
{
    public Class<?> type();
}
