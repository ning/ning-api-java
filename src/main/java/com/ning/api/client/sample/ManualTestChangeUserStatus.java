package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestChangeUserStatus
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, list single user of 'tatutest'");
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);
        // need to use content id here it seems:
        String userId = "688702:User:1";
        String msg = "Being tested-"+System.currentTimeMillis();
        System.out.println("Trying to update status of '"+userId+"', msg '"+msg+"'");
        try {
            User user = new User(userId);
            user.setStatusMessage(msg);
            conn.users().updater(user).update();
        } catch (Exception e) {
            System.err.println("Failed, problem = "+e);
            System.exit(1);
        }
        System.out.println("Call succeeded... let's verify status:");
        try {
            Users u = conn.users();
            User user = u.finder(UserField.email, UserField.statusMessage).find(userId);
            if (user == null) {
                System.err.println("No user '"+userId+"'?!?");
                System.exit(1);
            }
            System.out.println("Status now: '"+user.getStatusMessage()+"'");
        } catch (Exception e) {
            System.err.println("Failed, problem = "+e);
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Done!");
    }
}
