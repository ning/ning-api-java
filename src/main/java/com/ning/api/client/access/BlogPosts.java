package com.ning.api.client.access;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.NingClientConfig;
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

public class BlogPosts extends Items<BlogPost, BlogPostField>
{
    public BlogPosts(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "BlogPost", BlogPost.class, BlogPostField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Counter counter(ReadableDateTime createdAfter) {
        return new Counter(connection, config, endpointForCount(),
                createdAfter, null, null, null);
    }

    public Creator<BlogPost> creator(BlogPost blogPost) {
        return new BlogPostCreator(connection, config, endpointForPOST(), blogPost);
    }
    
    public final Deleter<BlogPost> deleter(Key<BlogPost> id) {
        return new DefaultDeleter<BlogPost>(connection, config, endpointForDELETE(), id);
    }
    
    public Lister listerForRecent(BlogPostField firstField, BlogPostField... otherFields) {
        return listerForRecent(new Fields<BlogPostField>(BlogPostField.class, firstField, otherFields));
    }

    public Lister listerForRecent(Fields<BlogPostField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields,
                null, null, null);
    }

    public Updater<BlogPost> updater(BlogPost blogPost) {
        return new BlogPostUpdater(connection, config, endpointForPUT(), blogPost);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Intermediate accessor used for building and executing "count" requests.
     * In addition to basic mandatory "createdAfter" also supports:
     * author (match), private=true/false, approved=true/false
     */
    public class Counter extends DefaultCounter
    {
        protected Counter(NingConnection connection, NingClientConfig config, String endpoint,
                ReadableDateTime createdAfter,
                String author, Boolean isPrivate, Boolean isApproved) {
            super(connection, config, endpoint, createdAfter, author, isPrivate, isApproved);
        }

        protected Counter(Counter base, String author, Boolean isPrivate, Boolean isApproved) {
            this(base.connection, base.config, base.endpoint, base.createdAfter,
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

    public static class BlogPostCreator extends DefaultCreator<BlogPost>
    {
        protected BlogPost blogPost;
        
        public BlogPostCreator(NingConnection connection, NingClientConfig config, String endpoint,
                BlogPost blogPost)
        {
            super(connection, config, endpoint);
            this.blogPost = blogPost.clone();
        }

        /**
         * Method for changing visibility that BlogPost being created will have.
         */
        public BlogPostCreator visibility(Visibility v) {
            this.blogPost = blogPost.clone();
            blogPost.setVisibility(v);
            return this;
        }

        /**
         * Method for indicating that the BlogPost being created will be a draft
         * (not published or to publish with specific time)
         */
        public BlogPostCreator draft() {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.draft);
            blogPost.setPublishTime(null);
            return this;
        }     

        /**
         * Method for indicating that the BlogPost being created will be published
         * immediately
         */
        public BlogPostCreator published() {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.publish);
            blogPost.setPublishTime(null);
            return this;
        }     

        /**
         * Method for indicating that the BlogPost being created will be published
         * at given date and time.
         */
        public BlogPostCreator published(ReadableDateTime publishTime) {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.queued);
            blogPost.setPublishTime(publishTime);
            return this;
        }     
        
        @Override
        protected NingHttpPost addCreateParameters(NingHttpPost create)
        {
            if (blogPost.getDescription() != null) {
                create = create.addFormParameter("description", blogPost.getDescription());
            }
            if (blogPost.getTitle() != null) {
                create = create.addFormParameter("title", blogPost.getTitle());
            }
            if (blogPost.getVisibility() != null) {
                create = create.addFormParameter("visibility", blogPost.getVisibility().toString());
            }
            if (blogPost.getPublishStatus() != null) {
                create = create.addFormParameter("publishStatus", blogPost.getPublishStatus().toString());
            }
            if (blogPost.getPublishTime() != null) {
                create = create.addFormParameter("publishTime", blogPost.getPublishTime().toString());
            }
            return create;
        }
    }
    
    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<BlogPost, BlogPostField>
    {
        protected Lister(NingConnection connection, NingClientConfig config, String endpoint,
                Fields<BlogPostField> fields,String author, Boolean isPrivate, Boolean isApproved)
        {
            super(connection, config, endpoint, fields, author, isPrivate, isApproved);
        }

        public Lister author(String author) {
            return new Lister(connection, config, endpoint, fields,
                    author, isPrivate, isApproved);
        }

        public Lister approved() {
            return new Lister(connection, config, endpoint, fields,
                    author, isPrivate, Boolean.TRUE);
        }
        
        public Lister unapproved() {
            return new Lister(connection, config, endpoint, fields,
                    author, isPrivate, Boolean.FALSE);
        }
        
        public Lister onlyPrivate() {
            return new Lister(connection, config, endpoint, fields,
                    author, Boolean.TRUE, isApproved);
        }

        public Lister onlyPublic() {
            return new Lister(connection, config, endpoint, fields,
                    author, Boolean.FALSE, isApproved);
        }

        @Override
        public PagedList<BlogPost> list() {
            return new PagedListImpl<BlogPost,BlogPostField>(connection, config, endpoint,
                    BlogPost.class, fields, author, isPrivate, isApproved);
        }
    }    

    public static class BlogPostUpdater extends DefaultUpdater<BlogPost>
    {
        protected BlogPost blogPost;

        protected BlogPostUpdater(NingConnection connection, NingClientConfig config, String endpoint,
                BlogPost blogPost)
        {
            super(connection, config, endpoint);
            this.blogPost = blogPost.clone();
        }

        /**
         * Method for changing visibility that BlogPost being created will have.
         */
        public BlogPostUpdater visibility(Visibility v) {
            this.blogPost = blogPost.clone();
            blogPost.setVisibility(v);
            return this;
        }

        /**
         * Method for indicating that the BlogPost being created will be a draft
         * (not published or to publish with specific time)
         */
        public BlogPostUpdater draft() {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.draft);
            blogPost.setPublishTime(null);
            return this;
        }     

        /**
         * Method for indicating that the BlogPost being created will be published
         * immediately
         */
        public BlogPostUpdater published() {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.publish);
            blogPost.setPublishTime(null);
            return this;
        }     

        /**
         * Method for indicating that the BlogPost being created will be published
         * at given date and time.
         */
        public BlogPostUpdater published(ReadableDateTime publishTime) {
            this.blogPost = blogPost.clone();
            blogPost.setPublishStatus(PublishStatus.queued);
            blogPost.setPublishTime(publishTime);
            return this;
        }     

        public BlogPostUpdater approved(Boolean approvedOrNot) {
            this.blogPost = blogPost.clone();
            blogPost.setApproved(approvedOrNot);
            return this;
        }
        
        @Override
        protected NingHttpPut addUpdateParameters(NingHttpPut put)
        {
            Key<BlogPost> id = blogPost.id();
            if (id == null) {
                throw new IllegalArgumentException("Missing mandatory field 'id'");
            }
            put = put.addFormParameter("id", id.toString());
            
            if (blogPost.getDescription() != null) {
                put = put.addFormParameter("description", blogPost.getDescription());
            }
            if (blogPost.getTitle() != null) {
                put = put.addFormParameter("title", blogPost.getTitle());
            }
            if (blogPost.getVisibility() != null) {
                put = put.addFormParameter("visibility", blogPost.getVisibility().toString());
            }
            if (blogPost.getPublishStatus() != null) {
                put = put.addFormParameter("publishStatus", blogPost.getPublishStatus().toString());
            }
            if (blogPost.getPublishTime() != null) {
                put = put.addFormParameter("publishTime", blogPost.getPublishTime().toString());
            }
            if (blogPost.isApproved() != null) {
                put = put.addFormParameter("approved", blogPost.isApproved().toString());
            }
            return put;
        }
    }

}
