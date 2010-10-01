package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.*;

public class ManualListNetworks
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
//        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);
        System.out.println("List networks:");
        int count = 0;
        for (Network network : conn.networks().listerForAlpha(NetworkField.author, NetworkField.name)) {
            ++count;
            System.out.println(" #"+count+": name="+network.getName()+", author="+network.getAuthor());
        }
        System.out.println("Done!");
    }
}
