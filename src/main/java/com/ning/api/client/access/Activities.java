package com.ning.api.client.access;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultCounter;
import com.ning.api.client.access.impl.DefaultDeleter;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

public class Activities extends Items<Activity, ActivityField>
{
    public Activities(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "Activity", Activity.class, ActivityField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public ActivityCounter counter(ReadableDateTime since) {
        return new ActivityCounter(connection, config, endpointForCount(), since, null);
    }

    public final Deleter<Activity> deleter(String id) {
        return deleter(new Key<Activity>(id));
    }
    public final Deleter<Activity> deleter(Key<Activity> id) {
        return new DefaultDeleter<Activity>(connection, config, endpointForDELETE(), id);
    }
    
    public Lister listerForRecent(ActivityField firstField, ActivityField... otherFields) {
        return listerForRecent(new Fields<ActivityField>(ActivityField.class, firstField, otherFields));
    }
    
    public Lister listerForRecent(Fields<ActivityField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields, null);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Intermediate accessor used for building and executing "count" requests.
     * Only additional parametrization (beyond 'createdAfter') is 'author',
     * user that caused Activities in question.
     */
    public class ActivityCounter extends DefaultCounter
    {
        protected ActivityCounter(NingConnection connection, NingClientConfig config, String endpoint,
                ReadableDateTime createdAfter, String author) {
            super(connection, config, endpoint, createdAfter, author, null, null);
        }

        public ActivityCounter author(String author) {
            return new ActivityCounter(connection, config, endpoint, createdAfter, author);
        }
    }

    public static class Lister extends DefaultLister<Activity, ActivityField>
    {
        protected Lister(NingConnection connection, NingClientConfig config, String endpoint,
                Fields<ActivityField> fields, String author)
        {
            super(connection, config, endpoint, fields, author, null, null);
        }

        public Lister author(String author) {
            return new Lister(connection, config, endpoint, fields, author);
        }

        @Override
        public PagedList<Activity> list() {
            return new PagedListImpl<Activity,ActivityField>(connection, config, endpoint,
                    Activity.class, fields, author, null, null);
        }
    }    

}
