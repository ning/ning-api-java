package com.ning.api.client.access;

/**
 * Simple container for opaque Anchor that XAPI returns and that is
 * passed by client for pagination.
 */
public class Anchor
{
    protected final String value;
    
    public Anchor(String value) {
        this.value = value;
    }

    @Override
    public String toString() { return value; }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != getClass()) return false;
        Anchor other = (Anchor) o;
        if (other == null) {
            return (value == null);
        }
        return other.value.equals(value);
    }
    
    @Override
    public int hashCode() { return value.hashCode(); }
}
