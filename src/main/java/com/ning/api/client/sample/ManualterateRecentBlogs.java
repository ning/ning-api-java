package com.ning.api.client.sample;

import java.util.*;

import com.ning.api.client.access.BlogPosts;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.*;

public class ManualterateRecentBlogs
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);
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
}
