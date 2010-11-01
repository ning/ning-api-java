package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.item.*;

public class ManualListPhotos extends SampleIntermediate
{
    public void doAction(NingConnection conn) throws Exception
    {
        System.out.println("Latest 20 photos:");
        int nr = 0;
        long startTime = System.currentTimeMillis();
        for (Photo photo : conn.photos().listerForRecent(PhotoField.title,
                PhotoField.author_fullName,
                PhotoField.author_url,
                PhotoField.author_iconUrl,
                PhotoField.image_url,
                PhotoField.image_width,
                PhotoField.image_height
        )) {
            ++nr;
            System.out.println("Post #"+nr+"; "+desc(photo));
        }
        long took = System.currentTimeMillis() - startTime;
        
        System.out.println("List, took "+took+" msecs");
    }

    private String desc(Photo photo)
    {
        String desc;
        Author auth = photo.getAuthorResource();
        if (auth == null) {
            desc = ", NO author";
        } else {
            desc = ", author="+auth.getFullName()
                +", author.url="+auth.getUrl()
                +", author.icon="+auth.getIconUrl();
        }
        Image image = photo.getImageResource();
        if (image == null) {
            desc += ", NO image data";
        } else {
            desc += ", image url: "+image.getUrl()
                +", image width: "+image.getWidth()
                +", image height: "+image.getHeight()
                ;
        }
        return desc;
    }

    
    public static void main(String[] args) throws Exception {
        new ManualListPhotos().action();
    }
}
