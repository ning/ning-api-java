package com.ning.api.client.tests;

import java.util.Iterator;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.access.Friends;
import com.ning.api.client.access.Friends.Lister;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.Friend;
import com.ning.api.client.item.FriendField;
import com.ning.api.client.item.FriendState;

@Test(groups = { "Friend" })
public class FriendsIT extends BaseIT {

    String friendScreenname;

    @Parameters({ "xapi-host", "http-port", "https-port", "subdomain",
            "consumer-key", "consumer-secret", "user-email", "user-password",
            "friend-screename" })
    public FriendsIT(String xapiHost, int defaultHttpPort,
            int DefaultHttpsPort, String subdomain, String consumerKey,
            String consumerSecret, String userEmail, String userPassword,
            String friendScreenname) {
        super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
                consumerKey, consumerSecret, userEmail, userPassword);
        this.friendScreenname = friendScreenname;
    }

    @Test
    public void Friends() {
        conn.friends();
    }

    @Test(groups = { "creators" })
    public void creator() {
        Friends friends = conn.friends();

        Friend friend = new Friend(friendScreenname);
        friends.creator(friend).create();
    }

    @Test(dependsOnMethods = { "creator" }, groups = { "deleters" })
    public void deleter() {
        Friends friends = conn.friends();

        Friend friend = new Friend(friendScreenname);
        friends.deleter(friend).delete();
    }

    @Test(groups = { "listers" })
    public void listerForRecent() {
        Friends friends = conn.friends();

        Fields<FriendField> fields = new Fields<FriendField>(FriendField.class);
        fields.add(FriendField.friend);
        fields.add(FriendField.state);

        Lister friendLister = friends.listerForRecent(fields);
        iterateFriends(friendLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentAuthor() {

        Friends friends = conn.friends();

        Fields<FriendField> fields = new Fields<FriendField>(FriendField.class);
        fields.add(FriendField.author);
        fields.add(FriendField.friend);
        fields.add(FriendField.state);

        Lister friendLister = friends.listerforAuthor(fields, friendScreenname);
        iterateFriends(friendLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentPendingState() {
        Friends friends = conn.friends();

        Fields<FriendField> fields = new Fields<FriendField>(FriendField.class);
        fields.add(FriendField.friend);
        fields.add(FriendField.state);

        Lister friendLister = friends.listerforState(fields,
                FriendState.pending);
        iterateFriends(friendLister);
    }

    /**
     * Iterate over the given Lister five times
     *
     * @param lister
     *            Lister to iterate over
     */
    private void iterateFriends(Lister lister) {
        int count = 0;
        Iterator<Friend> friendIterator = lister.iterator();
        while (friendIterator.hasNext()) {
            friendIterator.next();
            if (count > 5) {
                break;
            } else {
                count += 1;
            }
        }
    }

}
