package com.ning.api.client.sample;

import java.util.*;

import com.ning.api.client.access.*;
import com.ning.api.client.item.*;

public class ManualAddComment extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        // Just use hard-coded token. But iterate comments first:
        Comments comments = conn.comments();
        
        String blogId = "688702:BlogPost:41";

        List<Comment> old = listComments(comments, blogId);
        //List<Comment> old = new ArrayList<Comment>();

        // delete them?
        /*
        for (Comment comment : old) {
            System.out.println(" deleting comment "+comment.id()+"...");
            comments.deleter(comment.id()).delete();
        }
        */
        
        // then add
        System.out.println("Add new comment:");
        String desc = "Comment #"+(old.size()+1)+" double+good, 100%";
        Comment newComment = new Comment(new Key<BlogPost>(blogId), desc);
        comments.creator(newComment).create();
        // and list again
        System.out.println("Done!");
    }

    private static List<Comment> listComments(Comments comments, String blogId) throws Exception
    {
        System.out.println("Listing comments for '"+blogId+"':");
        ArrayList<Comment> result = new ArrayList<Comment>();
        for (Comment comment : comments.listerForRecent(new Key<BlogPost>(blogId),
                CommentField.id, CommentField.description, CommentField.author, CommentField.attachedToAuthor)) {
            result.add(comment);
            System.out.println(" #"+result.size()+": "+comment.id()+", desc="+comment.getDescription()+", author="+comment.getAuthor()+", attToAuthor="+comment.getAttachedToAuthor());
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        new ManualAddComment().action();
    }
}
