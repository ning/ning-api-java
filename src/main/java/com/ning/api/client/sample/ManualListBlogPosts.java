package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.item.Author;
import com.ning.api.client.item.BlogPost;
import com.ning.api.client.item.BlogPostField;

public class ManualListBlogPosts extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        System.out.println("Latest 20 blog posts:");
        int nr = 0;
        long startTime = System.currentTimeMillis();
        for (BlogPost post : conn.blogPosts().listerForRecent(BlogPostField.title,
                BlogPostField.author,
                BlogPostField.author_fullName,
                BlogPostField.author_url,
                BlogPostField.author_iconUrl
        )) {
            ++nr;
            Author auth = post.getAuthorResource();
            if (auth == null) {
                System.out.println("Post #"+nr+"; title="+post.getTitle()+", No author!");
            } else {
                System.out.println("Post #"+nr+"; title="+post.getTitle()
                        +", author="+auth.getFullName()
                        +", author.url="+auth.getUrl()
                        +", author.icon="+auth.getIconUrl()
                        );
            }
        }
        long took = System.currentTimeMillis() - startTime;
        
        System.out.println("List, took "+took+" msecs");
    }

    public static void main(String[] args) throws Exception {
        new ManualListBlogPosts().action();
    }
}
