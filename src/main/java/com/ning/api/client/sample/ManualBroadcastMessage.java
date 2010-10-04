package com.ning.api.client.sample;

import org.joda.time.DateTime;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.*;

public class ManualBroadcastMessage
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);

        String nowStr = new DateTime().toString();
        System.out.println("About to send message, time: "+nowStr);
        
        long startTime = System.currentTimeMillis();
        
        conn.broadcastMessages().creator(new BroadcastMessage("test broadcast at "+nowStr,
                "This is just a test for everyone, sent at "+nowStr+". Don't panic!")).create();
        long took = System.currentTimeMillis() - startTime;
        
        System.out.println("Sent, took "+took+" msecs");
    }

}
