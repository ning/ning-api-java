package com.ning.api.client.sample;

import java.util.*;

import org.joda.time.DateTime;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestListUsers
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, list all users of 'tatutest'");
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        // here's one pre-constructed token:
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        // and other, if we want to vary them
        //AuthEntry userToken = new AuthEntry("a7729d6b-06d2-4c77-90b1-e0deea57030e", "50f05a36-0ef3-45df-ab05-11b340aefa29");

        NingConnection conn = client.connect(userToken);

        // First, check users created since 7 days ago
        DateTime since = new DateTime();
        since = since.minusDays(6);
        int count = conn.users().counter(since).count();
        System.out.println("Users created since "+since+": "+count);
        
        List<User> users = null;
        try {
            Fields<UserField> fields = conn.users().fields(
                    UserField.birthDate,
                    UserField.email,
                    UserField.fullName,
                    UserField.statusMessage
                    );
            Users.UserLister lister = conn.users().listerForRecent(fields);
            PagedList<User> list = lister.list();
            users = list.next(5);
        } catch (Exception e) {
            System.err.println("Failed, problem ("+e.getClass().getName()+") = "+e);
            e.printStackTrace();
            System.exit(1);
        }
        
        System.out.println("Users found ("+users.size()+"):");
        for (User user : users) {
            if (user instanceof User) {
                User uc = (User) user;
                System.out.println(" User(c): id="+uc.id()+", name="+uc.getFullName()+", birthDate="+uc.getBirthDate()+", email="+uc.getEmail()
                        +", statusMessage="+uc.getStatusMessage());
            } else {
                System.out.println(" User(m): id="+user.id()+", created="+user.getCreatedDate()+", author="+user.getAuthor());
            }
        }
        System.out.println("Done!");
    }
}
