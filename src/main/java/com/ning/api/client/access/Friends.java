package com.ning.api.client.access;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultCreator;
import com.ning.api.client.access.impl.DefaultDeleter;
import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.Creator;
import com.ning.api.client.action.Deleter;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpRequest.Param;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Friend;
import com.ning.api.client.item.FriendField;
import com.ning.api.client.item.FriendState;
import com.ning.api.client.item.Key;

public class Friends extends Items<Friend, FriendField> {

    public Friends(NingConnection connection, NingClientConfig config) {
        super(connection, config, "Friend", Friend.class, FriendField.class);
    }

    /*
     * ///////////////////////////////////////////////////////////////////////
     * Public API: constructing request builders
     * ///////////////////////////////////////////////////////////////////////
     */

    public Lister listerForRecent(FriendField firstField,
            FriendField... otherFields) {
        return listerForRecent(new Fields<FriendField>(FriendField.class,
                firstField, otherFields));
    }

    public Lister listerForRecent(Fields<FriendField> fields) {
        return new Lister(connection, config, endpointForRecent(), fields,
                null, null);
    }

    public Lister listerforAuthor(Fields<FriendField> fields, String author) {
        return new Lister(connection, config, endpointForRecent(), fields,
                author, null);
    }

    public Lister listerforState(Fields<FriendField> fields, FriendState state) {
        return new Lister(connection, config, endpointForRecent(), fields,
                null, state);
    }

    protected static Param stateParam(FriendState state) {
        if (state == null) {
            return null;
        } else {
            return new Param("state", state.toString());
        }
    }

    public Creator<Friend> creator(Friend friend) {
        return new FriendCreator(connection, config, endpointForPOST(), friend);
    }

    public final Deleter<Friend> deleter(Friend friend) {
        return new FriendDeleter(connection, config, endpointForDELETE(),
                friend);
    }

    /*
     * ///////////////////////////////////////////////////////////////////////
     * Request builder classes (Creator, Updater, Finder, UserLister,
     * ActivityCounter)
     * ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Accessor used for fetching sequences of items
     */
    public static class Lister extends DefaultLister<Friend, FriendField> {

        /**
         * Friendship state to filter on
         */
        protected final FriendState state;

        protected Lister(NingConnection connection, NingClientConfig config,
                String endpoint, Fields<FriendField> fields, String author,
                FriendState state) {
            super(connection, config, endpoint, fields, author, null, null);
            this.state = state;
        }

        public Lister author(String author) {
            return new Lister(connection, config, endpoint, fields, author,
                    FriendState.friend);
        }

        @Override
        public PagedList<Friend> list() {
            return new PagedListImpl<Friend, FriendField>(connection, config,
                    endpoint, Friend.class, fields, author, null, null,
                    stateParam(this.state));
        }
    }

    public static class FriendCreator extends DefaultCreator<Friend> {
        protected Friend friend;

        public FriendCreator(NingConnection connection,
                NingClientConfig config, String endpoint, Friend friend) {
            super(connection, config, endpoint);
            this.friend = friend.clone();
        }

        @Override
        protected NingHttpPost addCreateParameters(NingHttpPost create) {
            if (friend.getFriend() != null) {
                create = create.addFormParameter("friend", friend.getFriend())
                        .addFormParameter("state",
                                FriendState.pending.toString());
            }
            return create;
        }
    }

    public static class FriendDeleter extends DefaultDeleter<Friend> {
        protected Friend friend;

        public FriendDeleter(NingConnection connection,
                NingClientConfig config, String endpoint, Friend friend) {
            super(connection, config, endpoint, friend.getFriend());
        }

        @Override
        protected NingHttpDelete buildRequest(Key<Friend> id) {
            NingHttpDelete deleter = connection.prepareHttpDelete(endpoint,
                    config);
            deleter = deleter.addAccept("*/*");
            deleter = deleter.addQueryParameter("friend", id.toString());
            return deleter;
        }

    }

}
