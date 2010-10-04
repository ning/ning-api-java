package com.ning.api.client.sample;

import java.util.*;

import org.joda.time.DateTime;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestListUsers extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
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

    public static void main(String[] args) throws Exception {
        new ManualTestListUsers().action();
    }
}
