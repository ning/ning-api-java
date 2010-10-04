package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestSingleUser extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        User user = null;
        try {
            // fields == null -> get whatever you get
//            user = conn.users().finder(null).find("NingDev");
            user = conn.users().finder(UserField.email).find("NingDev");
        } catch (Exception e) {
            System.err.println("Failed, problem = "+e);
            System.exit(1);
        }
        System.out.println("Call result: user = "+user);
    }

    public static void main(String[] args) throws Exception {
        new ManualTestSingleUser().action();
    }
}
