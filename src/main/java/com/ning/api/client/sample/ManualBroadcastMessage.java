package com.ning.api.client.sample;

import org.joda.time.DateTime;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.item.*;

public class ManualBroadcastMessage extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        String nowStr = new DateTime().toString();
        System.out.println("About to send message, time: "+nowStr);
        
        long startTime = System.currentTimeMillis();
        
        conn.broadcastMessages().creator(new BroadcastMessage("test broadcast at "+nowStr,
                "This is just a test for everyone, sent at "+nowStr+". Don't panic!")).create();
        long took = System.currentTimeMillis() - startTime;
        
        System.out.println("Sent, took "+took+" msecs");
    }

    public static void main(String[] args) throws Exception {
        new ManualBroadcastMessage().action();
    }
}
