package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;

public class ManualTestSingleUser
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, list single user of 'tatutest'");
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        // here's one pre-constructed token:
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        // and other, if we want to vary them
        //AuthEntry userToken = new AuthEntry("a7729d6b-06d2-4c77-90b1-e0deea57030e", "50f05a36-0ef3-45df-ab05-11b340aefa29");
        NingConnection conn = client.connect(userToken);
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
}
