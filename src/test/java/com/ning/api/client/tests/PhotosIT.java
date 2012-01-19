package com.ning.api.client.tests;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.access.Photos;
import com.ning.api.client.access.Photos.Lister;
import com.ning.api.client.item.Photo;
import com.ning.api.client.item.PhotoField;
import com.ning.api.client.tests.BaseIT;

@Test(groups = { "Photo" })
public class PhotosIT extends BaseIT {

    @Parameters( { "xapi-host", "http-port", "https-port", "subdomain",
            "consumer-key", "consumer-secret", "user-email", "user-password" })
    public PhotosIT(String xapiHost, int defaultHttpPort,
            int DefaultHttpsPort, String subdomain, String consumerKey,
            String consumerSecret, String userEmail, String userPassword) {
        super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
                consumerKey, consumerSecret, userEmail, userPassword);
    }

    @Test
    public void Photos() {
        conn.photos();
    }

    @Test(groups = { "creators", "skip" })
    public void creator() {
        throw new RuntimeException("Test not implemented");
    }

    @Test(dependsOnMethods = { "creator" }, groups = { "updaters", "skip" })
    public void updater() {
        Photo photo = fetchRecentPhoto();
        photo.setTitle("Java client was here");
        Photos photos = conn.photos();
        photos.updater(photo).update();
    }

    @Test(dependsOnMethods = { "updater" }, groups = { "deleters", "skip" })
    public void deleter() {
        Photo photo = fetchRecentPhoto();
        Photos photos = conn.photos();
        photos.deleter(photo.id()).delete();
    }

    @Test(groups = { "counters" })
    public void counter() {
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).count();
    }

    @Test(groups = { "counters" })
    public void counterApproved() {
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).approved().count();
    }

    @Test(groups = { "counters" })
    public void counterUnapproved() {
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).unapproved().count();
    }

    @Test(groups = { "counters" })
    public void counterPublic() {
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).onlyPublic().count();
    }

    @Test(groups = { "admin", "counters" })
    public void counterPrivate() {
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).onlyPrivate().count();
    }

    @Test(groups = { "counters" })
    public void counterForAuthor() {
        String userId = getCurrentUserAuthorString();
        Photos photos = conn.photos();
        DateTime now = new DateTime();
        photos.counter(now.minusDays(5)).author(userId).count();
    }

    @Test(groups = { "listers" })
    public void listerForRecent() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentApproved() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.approved();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentUnApproved() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.unapproved();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentPublic() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.onlyPublic();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "admin", "listers" })
    public void listerForRecentPrivate() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.onlyPrivate();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentForAuthor() {
        String userId = getCurrentUserAuthorString();
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.author(userId);
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeatured() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForFeatured(PhotoField.featuredDate);
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedApproved() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForFeatured(PhotoField.featuredDate);
        photoLister = photoLister.approved();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedUnApproved() {
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForFeatured(PhotoField.featuredDate);
        photoLister = photoLister.unapproved();
        iteratePhotos(photoLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedForAuthor() {
        String userId = getCurrentUserAuthorString();
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForFeatured(PhotoField.featuredDate);
        photoLister = photoLister.author(userId);
        iteratePhotos(photoLister);
    }

    /**
     * Return the most recent Photo that the current member uploaded
     *
     * @return Photo that the current member uploaded
     */
    private Photo fetchRecentPhoto() {
        String userId = getCurrentUserAuthorString();
        Photos photos = conn.photos();
        Lister photoLister = photos.listerForRecent(PhotoField.title);
        photoLister = photoLister.author(userId);
        Iterator<Photo> photoIterator = photoLister.iterator();
        assert photoIterator.hasNext() : "Current member has no photos to test with";
        return photoIterator.next();
    }

    /**
     * Iterate over the given Lister five times
     *
     * @param lister
     *            Lister to iterate over
     */
    private void iteratePhotos(Lister lister) {
        int count = 0;
        Iterator<Photo> photoIterator = lister.iterator();
        while (photoIterator.hasNext()) {
            photoIterator.next();
            if (count > 5) {
                break;
            } else {
                count += 1;
            }
        }
    }
}
