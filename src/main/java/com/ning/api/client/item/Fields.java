package com.ning.api.client.item;

import java.util.*;

/**
 * Base class for per-Item type field definition containers.
 *
 * @param <F> Enumeration that defines fields that are to be contained
 *   in this container
 */
public final class Fields<F extends Enum<F> & Typed>
{
    protected final Class<F> enumClass;
    
    protected final EnumSet<F> included;

    public Fields(Class<F> enumClass)
    {
        this(enumClass, EnumSet.noneOf(enumClass));
    }
    
    public Fields(Class<F> enumClass, F first, F... rest) {
        this(enumClass, EnumSet.of(first, rest));
    }

    public Fields(Class<F> enumClass, Collection<F> fields) {
        this(enumClass);
        included.addAll(fields);
    }
    
    private Fields(Class<F> enumClass, EnumSet<F> fields)
    {
        this.enumClass = enumClass;
        included = fields;
    }
    
    public Fields<F> add(F field) {
        included.add(field);
        return this;
    }

    public Fields<F> remove(F field) {
        included.remove(field);
        return this;
    }
    
    public boolean contains(F field) {
        return included.contains(field);
    }

    public boolean isEmpty() {
        return included.isEmpty();
    }
    
    public int size() {
        return included.size();
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (o == null) return false;
        if (o.getClass() != getClass()) return false;
        Fields<?> other = (Fields<?>) o;
        return (other.enumClass == enumClass) && included.equals(other.included);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(100);
        for (F entry : included) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            // important: use toString(), may have aliases
            sb.append(entry.toString());
        }
        return sb.toString();
    }
}
