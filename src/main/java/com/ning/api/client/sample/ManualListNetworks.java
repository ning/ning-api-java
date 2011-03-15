package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.item.*;

public class ManualListNetworks extends SampleIntermediate
{
    private final static String HOST = DEFAULT_XAPI_HOST;
    private final static int PORT_HTTP = DEFAULT_HTTP_PORT;
    private final static int PORT_HTTPS = DEFAULT_HTTPS_PORT;
    
    public ManualListNetworks() {
        super(HOST, PORT_HTTP, PORT_HTTPS, "www");
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
