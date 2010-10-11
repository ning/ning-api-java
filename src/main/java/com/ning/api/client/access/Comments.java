package com.ning.api.client.access;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultCreator;
import com.ning.api.client.access.impl.DefaultDeleter;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.Creator;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpRequest.Param;
import com.ning.api.client.item.*;

public class Comments extends Items<Comment, CommentField>
{
    public Comments(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "Comment", Comment.class, CommentField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public final Deleter<Comment> deleter(Key<Comment> id) {
        return new DefaultDeleter<Comment>(connection, config, endpointForDELETE(), id);
    }

    /**
     * @param attachedTo Item that comments to retrieve are attached to
     */
    public Lister listerForRecent(Key<?> attachedTo, CommentField firstField, CommentField... otherFields) {
        return listerForRecent(attachedTo, new Fields<CommentField>(CommentField.class, firstField, otherFields));
    }

    /**
     * @param attachedTo Item that comments to retrieve are attached to
     */
    public Lister listerForRecent(Key<?> attachedTo, Fields<CommentField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields,
                null, attachedTo);
    }

    public Creator<Comment> creator(Comment comment) {
        return new CommentCreator(connection, config, endpointForPOST(), comment);
    }
    
    protected static Param attachedToParam(Key<?> attachedTo)
    {
        return new Param("attachedTo", attachedTo.toString());
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Comment, CommentField>
    {
        protected final Key<?> attachedTo;
        
        protected Lister(NingConnection connection, NingClientConfig config, String endpoint,
                Fields<CommentField> fields, Boolean isApproved, Key<?> attachedTo)
        {
            super(connection, config, endpoint, fields, null, null, isApproved);
            this.attachedTo = attachedTo;
        }

        public Lister approved() {
            return new Lister(connection, config, endpoint, fields,
                    Boolean.TRUE, attachedTo);
        }
        
        public Lister unapproved() {
            return new Lister(connection, config, endpoint, fields,
                    Boolean.FALSE, attachedTo);
        }
        
        @Override
        public PagedList<Comment> list() {
            return new PagedListImpl<Comment,CommentField>(connection, config, endpoint,
                    Comment.class, fields, null, null, isApproved,
                    attachedToParam(attachedTo));
        }
    }    

    public static class CommentCreator extends DefaultCreator<Comment>
    {
        protected final Key<? extends ContentItem<?,?>> attachedTo;
        protected final String description;
        
        public CommentCreator(NingConnection connection, NingClientConfig config, String endpoint,
                Comment comment)
        {
            super(connection, config, endpoint);
            attachedTo = comment.getAttachedTo();
            description = comment.getDescription();
        }

        @Override
        protected NingHttpPost addCreateParameters(NingHttpPost create)
        {
            if (attachedTo != null) {
                create = create.addFormParameter("attachedTo", attachedTo.toString());
            }
            if (description != null) {
                create = create.addFormParameter("description", description);
            }
            return create;
        }
    }
}
