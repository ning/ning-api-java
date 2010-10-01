package com.ning.api.client.access;

import java.util.List;

import org.joda.time.ReadableDateTime;

import com.ning.api.client.access.impl.DefaultCounter;
import com.ning.api.client.access.impl.DefaultFinder;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.DefaultUpdater;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.action.Updater;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpPut;
import com.ning.api.client.http.NingHttpRequest.Param;
import com.ning.api.client.item.*;

/**
 * Helper class that encapsulates subset of API used for accessing
 * User ({@link User}, {@link UserMinimal}) content items.
 */
public class Users extends Items<User, UserField>
{
    public Users(NingConnection connection)
    {
        super(connection, "User", User.class, UserField.class);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    // 
    // note: some custom variants due to extended lookup options
    ///////////////////////////////////////////////////////////////////////
     */

    public Counter counter(ReadableDateTime createdAfter) {
        return new Counter(connection, defaultTimeoutForReadsMsecs, endpointForCount(),
                createdAfter, null, null);
    }

    @Override
    public UserFinder finder(UserField firstField, UserField... otherFields) {
        return finder(new Fields<UserField>(UserField.class, firstField, otherFields));
    }
    
    @Override
    public UserFinder finder(Fields<UserField> fields) {
        return new UserFinder(connection, defaultTimeoutForReadsMsecs, endpointForSingle(), fields);
    }

    public UserLister listerForAlpha(UserField firstField, UserField... otherFields) {
        return listerForAlpha(new Fields<UserField>(UserField.class, firstField, otherFields));
    }

    public UserLister listerForAlpha(Fields<UserField> fields) {
        return new UserLister(connection, defaultTimeoutForReadsMsecs, endpointForAlpha(), fields,
                null, null, null);
    }

    public UserLister listerForRecent(UserField firstField, UserField... otherFields) {
        return listerForRecent(new Fields<UserField>(UserField.class, firstField, otherFields));
    }

    public UserLister listerForRecent(Fields<UserField> fields) {
        return new UserLister(connection, defaultTimeoutForReadsMsecs, endpointForRecent(), fields,
                null, null, null);
    }

    /**
     * Constructs updater for updating user specific by given User object
     */
    public Updater<User> updater(User user) {
        return new UserUpdater(connection, defaultTimeoutForUpdatesMsecs, endpointForPUT(), user);
    }

    /**
     * Constructs updater for updating current user (user whose OAuth credentials are used
     * by client)
     */
    public Updater<User> updater() {
        return new UserUpdater(connection, defaultTimeoutForUpdatesMsecs, endpointForPUT(), new User());
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, ActivityCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    /**
     * Intermediate accessor used for building and executing "count" requests.
     * In addition to basic mandatory "createdAfter" also supports:
     * author (match), approved=false, member=true
     */
    public static class Counter extends DefaultCounter
    {
        protected final Boolean isMember;
        
        protected Counter(NingConnection connection, long timeoutMsecs, String endpoint,
                ReadableDateTime createdAfter, 
                Boolean isApproved, Boolean isMember)
        {
            super(connection, timeoutMsecs, endpoint, createdAfter, null, null, isApproved);
            this.isMember = isMember;
        }

        // no way to only include approved ones currently, so:
        public Counter unapproved() {
            return new Counter(connection, timeoutMsecs, endpoint, createdAfter, Boolean.FALSE, isMember);
        }

        public Counter onlyMembers() {
            return new Counter(connection, timeoutMsecs, endpoint, createdAfter, isApproved, Boolean.TRUE);
        }
        
        @Override
        protected NingHttpGet buildQuery() {
            NingHttpGet query = super.buildQuery();
            if (isMember != null) {
                query = query.addQueryParameter("isMember", isMember.toString());
            }
            return query;
        }
    }
    
    /**
     * Intermediate accessor used for building and executing "find" requests
     */
    public static class UserFinder extends DefaultFinder<User, UserField>
    {
        public UserFinder(NingConnection connection, long timeoutMsecs, String endpoint, Fields<UserField> fields) {
            super(connection, timeoutMsecs, endpoint, User.class, fields);
        }

        // TODO: add "findUserByAuthorName()" variants!

        public User findByAuthor(String author)
        {
            NingHttpGet getter = prepareQuery();
            getter = getter.addQueryParameter("author", author);
            return getter.execute(timeoutMsecs).asSingleItem(itemType);
        }

        public List<User> findByAuthors(String... authors) {
            // !!! TODO
            return null;
        }
    }

    /**
     * Accessor used for fetching sequences of items
     */
    public static class UserLister extends DefaultLister<User, UserField>
    {
        protected final Boolean isMember;

        protected UserLister(NingConnection connection, long timeoutMsecs, String endpoint,
                Fields<UserField> fields,String author, Boolean isApproved, Boolean isMember)
        {
            super(connection, timeoutMsecs, endpoint, fields, author, null, isApproved);
            this.isMember = isMember;
        }

        public UserLister author(String author) {
            return new UserLister(connection, timeoutMsecs, endpoint, fields,
                    author, isApproved, isMember);
        }

        
        // no way to only include approved ones currently, so:
        public UserLister unapproved() {
            return new UserLister(connection, timeoutMsecs, endpoint, fields,
                    author, Boolean.FALSE, isMember);
        }

        public UserLister onlyMembers() {
            return new UserLister(connection, timeoutMsecs, endpoint, fields,
                    author, isApproved, Boolean.TRUE);
        }

        @Override
        public PagedList<User> list() {
            Param memberParam = (isMember == null) ? null : new Param("isMember", isMember.toString());

            return new PagedListImpl<User,UserField>(connection, timeoutMsecs, endpoint,
                    User.class, fields, author, null, null, memberParam);
        }
    }    

    public static class UserUpdater extends DefaultUpdater<User>
    {
        protected User user;

        protected UserUpdater(NingConnection connection, long timeoutMsecs, String endpoint,
                User user)
        {
            super(connection, timeoutMsecs, endpoint);
            this.user = user;
        }

        @Override
        protected NingHttpPut addUpdateParameters(NingHttpPut put)
        {
            if (user.getStatusMessage() != null) {
                put = put.addFormParameter("statusMessage", user.getStatusMessage());
            }
            if (user.isApproved() != null) {
                put = put.addFormParameter("approved", user.isApproved().toString());
            }
            return put;
        }

        public UserUpdater approved(Boolean approvedOrNot) {
            this.user = user.clone();
            user.setApproved(approvedOrNot);
            return this;
        }
    }
}
