package com.ning.api.client.sample;

import org.joda.time.DateTime;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.*;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.BlogPost;

public class ManualAddBlogPost
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);

        // Just use hard-coded token. But iterate comments first:
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        String nowStr = now.toString();

        long startTime = System.currentTimeMillis();
        int count = posts.counter(now.minusDays(5)).count();
        long took = System.currentTimeMillis() - startTime;
        System.out.println("Existing blogs during past 5 days: "+count+" (took "+took+" msecs)");
        
        // then add
        System.out.println("Add new post:");
        String title = "Blog post #"+count+", at "+nowStr;
        String desc = "Test post, sent via Java client";
        BlogPost post = new BlogPost(title, desc);
        post.setPublishTime(now);
        startTime = System.currentTimeMillis();
        posts.creator(post).create();
        took = System.currentTimeMillis() - startTime;
        
        System.out.println("Created, took "+took+" msecs");
    }

}
