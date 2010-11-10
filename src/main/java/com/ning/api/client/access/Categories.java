package com.ning.api.client.access;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

public class Categories
    extends Items<Category, CategoryField>
{
    public Categories(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "Category", Category.class, CategoryField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Lister listerForAlpha(CategoryField firstField, CategoryField... otherFields) {
        return listerForAlpha(new Fields<CategoryField>(CategoryField.class, firstField, otherFields));
    }

    public Lister listerForAlpha(Fields<CategoryField> fields) {
        return new Lister(connection, config, endpointForAlpha(), fields);
    }
    
    public Lister listerForRecent(CategoryField firstField, CategoryField... otherFields) {
        return listerForRecent(new Fields<CategoryField>(CategoryField.class, firstField, otherFields));
    }

    public Lister listerForRecent(Fields<CategoryField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Category, CategoryField>
    {
        protected Lister(NingConnection connection, NingClientConfig config, String endpoint,
                Fields<CategoryField> fields)
        {
            super(connection, config, endpoint, fields, null, null, null);
        }

        @Override
        public PagedList<Category> list() {
            return new PagedListImpl<Category,CategoryField>(connection, config, endpoint,
                    Category.class, fields, author, isPrivate, isApproved);
        }
    }
}
