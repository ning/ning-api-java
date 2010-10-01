package com.ning.api.client.access;

import com.ning.api.client.access.impl.DefaultFinder;
import com.ning.api.client.action.Finder;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Typed;

/**
 * Base class to contain shared functionality and constants for
 * conceptual groups of objects (which are essentially accessors,
 * or request builders for modifying individual items or groups
 * of items of specified type).
 * 
 * @author tatu
 *
 * @param <C> Type of Content item
 * @param <F> Type of enumeration that defines accessible fields
 *    of content item (of type C)
 */
public abstract class Items<
   C extends ContentItem<F, C>, // item type
   F extends Enum<F> & Typed // set of fields used for filtering
>
{
    // // // Misc constants
    
    protected final NingConnection connection;

    /**
     * Part of end point path that differs between resource type; typically
     * just name of the resource type (like "User")
     */
    protected final String endpointBase;

    protected final Class<C> itemType;

    protected final Class<F> fieldType;
    
    /**
     * Timeout period to use for read methods.
     */
    protected final long defaultTimeoutForReadsMsecs;

    /**
     * Timeout period to use for update (PUT, POST) methods.
     */
    protected final long defaultTimeoutForUpdatesMsecs;
    
    protected Items(NingConnection connection, String endpointBase,
            Class<C> itemType, Class<F> fieldType)
    {
        this.connection = connection;
        this.endpointBase = endpointBase;
        this.itemType = itemType;
        this.fieldType = fieldType;
        defaultTimeoutForReadsMsecs = connection.getDefaultTimeoutMsecs();
        
        // for now let's use twice as long timeout period for updates as reads:
        defaultTimeoutForUpdatesMsecs = defaultTimeoutForReadsMsecs * 2;
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    // non-final to allow custom Finder types
    public Finder<C> finder(F firstField, F... otherFields) {
        return finder(new Fields<F>(fieldType, firstField, otherFields));
    }

    // non-final to allow custom Finder types
    public Finder<C> finder(Fields<F> fields) {
        return new DefaultFinder<C,F>(connection, defaultTimeoutForReadsMsecs, endpointForRecent(),
                itemType, fields);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, constructing standard paths
    ///////////////////////////////////////////////////////////////////////
     */

    protected String endpointForSingle() {
        return endpointBase;
    }

    protected String endpointForDELETE() {
        return endpointBase;
    }
    
    protected String endpointForPUT() {
        return endpointBase;
    }

    protected String endpointForPOST() {
        return endpointBase;
    }
    
    protected String endpointForAlpha() {
        return endpointBase+"/alpha";
    }

    protected String endpointForRecent() {
        return endpointBase+"/recent";
    }

    protected String endpointForCount() {
        return endpointBase+"/count";
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, filter construction
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Helper method for constructing empty field set
     */
    public Fields<F> fields()
    {
        return new Fields<F>(fieldType);
    }

    /**
     * Helper method for constructing field sets to use for filtering
     */
    public Fields<F> fields(F first, F... other)
    {
        return new Fields<F>(fieldType, first, other);
    }
}
