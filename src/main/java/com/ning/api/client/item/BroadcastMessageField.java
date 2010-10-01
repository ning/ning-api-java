package com.ning.api.client.item;

/**
 * Dummy enumeration that we need for API, although there is no read access
 * to broadcast messages.
 */
public enum BroadcastMessageField implements Typed
{
    subject(String.class),
    body(String.class),
    messageId(String.class)
    ;

    private final Class<?> valueType;

    private BroadcastMessageField(Class<?> valueType) {
        this.valueType = valueType;
    }
    
    @Override
    public Class<?> type() { return valueType; }
}
