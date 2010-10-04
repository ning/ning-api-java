package com.ning.api.client.sample;

import java.util.List;

import com.ning.api.client.access.Activities;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

public class ManualIterateActivities extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
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

    public static void main(String[] args) throws Exception {
        new ManualIterateActivities().action();
    }
}
