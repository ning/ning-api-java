package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.item.*;

public class ManualListNetworks extends SampleIntermediate
{
    public ManualListNetworks() {
        super();
    }
    
    public void doAction(NingConnection conn) throws Exception
    {
        System.out.println("List networks:");
        int count = 0;
        for (Network network : conn.networks().listerForAlpha(NetworkField.author, NetworkField.name)) {
            ++count;
            System.out.println(" #"+count+": name="+network.getName()+", author="+network.getAuthor());
        }
        System.out.println("Done!");
    }

    public static void main(String[] args) throws Exception {
        new ManualListNetworks().action();
    }
}
