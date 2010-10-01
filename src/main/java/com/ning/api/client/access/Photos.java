package com.ning.api.client.access;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.access.impl.DefaultCounter;
import com.ning.api.client.access.impl.DefaultCreator;
import com.ning.api.client.access.impl.DefaultDeleter;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.DefaultUpdater;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.Creator;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.action.Updater;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpPut;
import com.ning.api.client.item.*;

public class Photos extends Items<Photo, PhotoField>
{
    public Photos(NingConnection connection)
    {
        super(connection, "Photo", Photo.class, PhotoField.class);
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

    public Creator<Photo> creator(Photo photo) {
        return new PhotoCreator(connection, defaultTimeoutForUpdatesMsecs, endpointForPOST(), photo);
    }
    
    public final Deleter<Photo> deleter(Key<Photo> id) {
        return new DefaultDeleter<Photo>(connection, defaultTimeoutForUpdatesMsecs, endpointForDELETE(), id);
    }
    
    public Lister listerForRecent(PhotoField firstField, PhotoField... otherFields) {
        return listerForRecent(new Fields<PhotoField>(PhotoField.class, firstField, otherFields));
    }

    public Lister listerForRecent(Fields<PhotoField> fields) {
        return new Lister(connection, defaultTimeoutForReadsMsecs, endpointForRecent(), fields,
                null, null, null);
    }

    public Updater<Photo> updater(Photo photo) {
        return new PhotoUpdater(connection, defaultTimeoutForUpdatesMsecs, endpointForPUT(), photo);
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

    public static class PhotoCreator extends DefaultCreator<Photo>
    {
        protected Photo photo;
        
        public PhotoCreator(NingConnection connection, long timeoutMsecs, String endpoint,
                Photo Photo)
        {
            super(connection, timeoutMsecs, endpoint);
            this.photo = photo.clone();
        }

        /**
         * Method for changing visibility that Photo being created will have.
         */
        public PhotoCreator visibility(Visibility v) {
            this.photo = photo.clone();
            photo.setVisibility(v);
            return this;
        }

        @Override
        protected NingHttpPost addCreateParameters(NingHttpPost create)
        {
            if (photo.getDescription() != null) {
                create = create.addFormParameter("description", photo.getDescription());
            }
            if (photo.getTitle() != null) {
                create = create.addFormParameter("title", photo.getTitle());
            }
            if (photo.getVisibility() != null) {
                create = create.addFormParameter("visibility", photo.getVisibility().toString());
            }
            return create;
        }
    }
    
    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Photo, PhotoField>
    {
        protected Lister(NingConnection connection, long timeoutMsecs, String endpoint,
                Fields<PhotoField> fields,String author, Boolean isPrivate, Boolean isApproved)
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
        public PagedList<Photo> list() {
            return new PagedListImpl<Photo,PhotoField>(connection, timeoutMsecs, endpoint,
                    Photo.class, fields, author, isPrivate, isApproved);
        }
    }    
    
    public static class PhotoUpdater extends DefaultUpdater<Photo>
    {
        protected Photo photo;

        protected PhotoUpdater(NingConnection connection, long timeoutMsecs, String endpoint,
                Photo photo)
        {
            super(connection, timeoutMsecs, endpoint);
            this.photo = photo.clone();
        }

        /**
         * Method for changing visibility that Photo being created will have.
         */
        public PhotoUpdater visibility(Visibility v) {
            this.photo = photo.clone();
            photo.setVisibility(v);
            return this;
        }

        public PhotoUpdater approved(Boolean approvedOrNot) {
            this.photo = photo.clone();
            photo.setApproved(approvedOrNot);
            return this;
        }
        
        @Override
        protected NingHttpPut addUpdateParameters(NingHttpPut put)
        {
            Key<Photo> id = photo.id();
            if (id == null) {
                throw new IllegalArgumentException("Missing mandatory field 'id'");
            }
            put = put.addFormParameter("id", id.toString());
            
            if (photo.getDescription() != null) {
                put = put.addFormParameter("description", photo.getDescription());
            }
            if (photo.getTitle() != null) {
                put = put.addFormParameter("title", photo.getTitle());
            }
            if (photo.getVisibility() != null) {
                put = put.addFormParameter("visibility", photo.getVisibility().toString());
            }
            if (photo.isApproved() != null) {
                put = put.addFormParameter("approved", photo.isApproved().toString());
            }
            return put;
        }
    }
}
