package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualChangeUserStatus extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
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

    public static void main(String[] args) throws Exception {
        new ManualChangeUserStatus().action();
    }
}
