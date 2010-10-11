package com.ning.api.client.sample;

import java.util.*;

import com.ning.api.client.access.BlogPosts;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

public class ManualIterateRecentBlogs extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        BlogPosts b = conn.blogPosts();
        int round = 1;
        
        List<BlogPost> blogs = null;
        BlogPosts.Lister lister = b.listerForRecent(BlogPostField.approved,
                BlogPostField.commentCount, BlogPostField.publishStatus, BlogPostField.publishTime,
                BlogPostField.title);
        PagedList<BlogPost> list = lister.list();
        System.out.println("First, iterate over list in chunks of 3");
        do {
            System.out.println("Request #"+round+" (anchor="+list.position()+"):");
            blogs = list.next(3);
            for (int i = 0, len = blogs.size(); i < len; ++i) {
                BlogPost blog = (BlogPost) blogs.get(i);
                System.out.println(" post #"+(i+1)+" -> "+toString(blog));
            }
            ++round;
            
        } while (!blogs.isEmpty());
        System.out.println();
        System.out.println("Then, one by one (Iterable); and fetch comments too");
        round = 1;
        for (BlogPost post : lister) {
            System.out.println(" Post #"+post+" "+toString(post));
            System.out.println("  comments:");
            for (Comment comment : conn.comments().listerForRecent(post.id(), CommentField.author, CommentField.description)) {
                System.out.println("    # "+toString(comment));
            }
            ++round;
        }
        System.out.println("Done!");
    }

    static String toString(BlogPost blog)
    {
        return "approved="+blog.isApproved()+", publishStatus="
            +blog.getPublishStatus()+", publishTime="+blog.getPublishTime()+", title="+blog.getTitle();
    }

    static String toString(Comment comment)
    {
        return "author: "+comment.getAuthor()+", description: "+comment.getDescription();
    }

    public static void main(String[] args) throws Exception {
        new ManualIterateRecentBlogs().action();
    }
}
