package com.ning.api.client.sample;

import java.util.*;

import org.joda.time.DateTime;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.item.Fields;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestListUsers extends SampleIntermediate
{
    public ManualTestListUsers() {
//        super(DEFAULT_XAPI_HOST, 80, 443, "tatutest");
        super("localhost", 9090, 8443, "tatutest");
    }

    @Override
    protected ConsumerKey getConsumerKey() {
        // Tatutest/prod
//        return new ConsumerKey("acb23d23-f820-4200-afac-6c4ed81f5a97", "40b6d790-d950-488e-af88-ede9d237a9bc");
        // tatutest/local
      return new ConsumerKey("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e");
    }

    @Override
    public RequestToken getUserToken() {
        // Tatutest/prod
//        return new RequestToken("6a25e654-b1a6-4559-8074-dbfbfdc8465f", "c042818d-d996-4f2a-a975-dbe241de6b7b");
        // tatutest/local
        return new RequestToken("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
    }
    
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
