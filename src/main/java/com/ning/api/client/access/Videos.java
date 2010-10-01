package com.ning.api.client.access;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.access.impl.DefaultCounter;
import com.ning.api.client.access.impl.DefaultDeleter;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

public class Videos extends Items<Video, VideoField>
{
    public Videos(NingConnection connection)
    {
        super(connection, "Video", Video.class, VideoField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Counter counter(ReadableDateTime createdAfter) {
        return new Counter(connection, defaultTimeoutForReadsMsecs, endpointForCount(),
                createdAfter, null, null, null);
    }

    public final Deleter<Video> deleter(Key<Video> id) {
        return new DefaultDeleter<Video>(connection, defaultTimeoutForUpdatesMsecs, endpointForDELETE(), id);
    }
    
    public Lister listerForRecent(VideoField firstField, VideoField... otherFields) {
        return listerForRecent(new Fields<VideoField>(VideoField.class, firstField, otherFields));
    }

    public Lister listerForRecent(Fields<VideoField> fields) {
        return new Lister(connection, defaultTimeoutForReadsMsecs, endpointForRecent(), fields,
                null, null, null);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Intermediate accessor used for building and executing "count" requests.
     * In addition to basic mandatory "createdAfter" also supports:
     * author (match), private=true/false, approved=false
     */
    public class Counter extends DefaultCounter
    {
        protected Counter(NingConnection connection, long timeoutMsecs, String endpoint,
                ReadableDateTime createdAfter, String author,
                Boolean isPrivate, Boolean isApproved) {
            super(connection, timeoutMsecs, endpoint, createdAfter, author, isPrivate, isApproved);
        }

        protected Counter(Counter base, String author, Boolean isPrivate, Boolean isApproved) {
            this(base.connection, base.timeoutMsecs, base.endpoint, base.createdAfter,
                    author, isPrivate, isApproved);
        }
        
        public Counter author(String author) {
            return new Counter(this, author, isPrivate, isApproved);
        }

        public Counter approved() {
            return new Counter(this, author, isPrivate, Boolean.TRUE);
        }
        
        public Counter unapproved() {
            return new Counter(this, author, isPrivate, Boolean.FALSE);
        }

        public Counter onlyPrivate() {
            return new Counter(this, author, Boolean.TRUE, isApproved);
        }

        public Counter onlyPublic() {
            return new Counter(this, author, Boolean.FALSE, isApproved);
        }
    }

    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Video, VideoField>
    {
        protected Lister(NingConnection connection, long timeoutMsecs, String endpoint,
                Fields<VideoField> fields,String author, Boolean isPrivate, Boolean isApproved)
        {
            super(connection, timeoutMsecs, endpoint, fields, author, isPrivate, isApproved);
        }

        public Lister author(String author) {
            return new Lister(connection, timeoutMsecs, endpoint, fields,
                    author, isPrivate, isApproved);
        }

        public Lister approved() {
            return new Lister(connection, timeoutMsecs, endpoint, fields,
                    author, isPrivate, Boolean.TRUE);
        }

        public Lister unapproved() {
            return new Lister(connection, timeoutMsecs, endpoint, fields,
                    author, isPrivate, Boolean.FALSE);
        }
        
        public Lister onlyPrivate() {
            return new Lister(connection, timeoutMsecs, endpoint, fields,
                    author, Boolean.TRUE, isApproved);
        }

        public Lister onlyPublic() {
            return new Lister(connection, timeoutMsecs, endpoint, fields,
                    author, Boolean.FALSE, isApproved);
        }

        @Override
        public PagedList<Video> list() {
            return new PagedListImpl<Video,VideoField>(connection, timeoutMsecs, endpoint,
                    Video.class, fields, author, isPrivate, isApproved);
        }
    }    

}
