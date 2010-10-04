package com.ning.api.client.sample;

import java.util.*;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.*;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.*;

public class ManualAddComment
{
    public static void main(String[] args) throws Exception
    {
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        AuthEntry userToken = new AuthEntry("878ace49-f324-403b-85c9-3d78117147e1", "12913470-6dee-4944-8bbc-661401fca07a");
        NingConnection conn = client.connect(userToken);

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
}
