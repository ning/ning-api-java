package com.ning.api.client.access;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.http.NingHttpRequest.Param;
import com.ning.api.client.item.*;

public class Topics extends Items<Topic, TopicField>
{
    public Topics(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "Topic", Topic.class, TopicField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Lister listerForAlpha(TopicField firstField, TopicField... otherFields) {
        return listerForAlpha(new Fields<TopicField>(TopicField.class, firstField, otherFields));
    }

    public Lister listerForAlpha(Fields<TopicField> fields) {
        return new Lister(connection, config, endpointForAlpha(), fields, null);
    }
    
    public Lister listerForRecent(TopicField firstField, TopicField... otherFields) {
        return listerForRecent(new Fields<TopicField>(TopicField.class, firstField, otherFields));
    }

    public Lister listerForRecent(Fields<TopicField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields, null);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Topic, TopicField>
    {
        private Key<Category> forCategory;
        
        protected Lister(NingConnection connection, NingClientConfig config, String endpoint,
                Fields<TopicField> fields,
                Key<Category> category)
        {
            super(connection, config, endpoint, fields, null, null, null);
            forCategory = category;
        }

        public Lister category(Key<Category> category) {
            return new Lister(connection, config, endpoint, fields, category);
        }

        @Override
        public PagedList<Topic> list() {
            Param categoryParam = (forCategory == null) ? null : new Param("categoryId", forCategory.toString());
            return new PagedListImpl<Topic,TopicField>(connection, config, endpoint,
                    Topic.class, fields, author, isPrivate, isApproved,
                    categoryParam);
        }
    }
}
