package com.ning.api.client.sample;

import java.util.List;

import com.ning.api.client.access.Activities;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.*;

public class ManualIterateActivities
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);
        Activities a = conn.activities();
        int entry = 0;
        
        List<Activity> acts = null;
        Activities.Lister lister = a.listerForRecent(ActivityField.title, ActivityField.type,
                ActivityField.author);
        PagedList<Activity> list = lister.list();
        System.out.println("First, iterate over list in chunks of 3");
        do {
            System.out.println("Request #"+(entry/3)+" (anchor="+list.position()+"):");
            acts = list.next(3);
            for (Activity act : acts) {
                ++entry;
                System.out.println(" activity #"+entry+" -> "+toString(act));
            }
        } while (!acts.isEmpty());
        System.out.println("Done!");
    }

    static String toString(Activity act)
    {
        return "activity of type "+act.getType()+", title '"+act.getTitle()+"', author: "+act.getAuthor();
    }

}
