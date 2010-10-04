package com.ning.api.client.sample;

import org.joda.time.DateTime;

import com.ning.api.client.access.*;
import com.ning.api.client.item.BlogPost;

public class ManualAddBlogPost extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
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

    public static void main(String[] args) throws Exception {
        new ManualAddBlogPost().action();
    }
}
