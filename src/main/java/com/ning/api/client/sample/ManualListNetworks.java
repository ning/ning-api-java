package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.item.*;

public class ManualListNetworks extends SampleIntermediate
{
    public ManualListNetworks() {
        super(DEFAULT_XAPI_HOST, 80, 443, "tatutest");
        //super("localhost", 9090, 8443, "tatutest");
    }

    @Override
    protected ConsumerKey getConsumerKey() {
        // Tatutest/prod
        return new ConsumerKey("acb23d23-f820-4200-afac-6c4ed81f5a97", "40b6d790-d950-488e-af88-ede9d237a9bc");
        // tatutest/local
//      return new ConsumerKey("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e");
    }

    @Override
    public RequestToken getUserToken() {
        // Tatutest/prod
        return new RequestToken("6a25e654-b1a6-4559-8074-dbfbfdc8465f", "c042818d-d996-4f2a-a975-dbe241de6b7b");
        // tatutest/local
//        return new RequestToken("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
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
