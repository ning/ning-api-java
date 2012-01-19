package com.ning.api.client.tests;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.access.BlogPosts;
import com.ning.api.client.access.BlogPosts.Lister;
import com.ning.api.client.item.BlogPost;
import com.ning.api.client.item.BlogPostField;

@Test(groups = { "BlogPost" })
public class BlogPostsIT extends BaseIT {

    @Parameters( { "xapi-host", "http-port", "https-port", "subdomain",
            "consumer-key", "consumer-secret", "user-email", "user-password" })
    public BlogPostsIT(String xapiHost, int defaultHttpPort,
            int DefaultHttpsPort, String subdomain, String consumerKey,
            String consumerSecret, String userEmail, String userPassword) {
        super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
                consumerKey, consumerSecret, userEmail, userPassword);
    }

    @Test
    public void BlogPosts() {
        conn.blogPosts();
    }

    @Test(groups = { "creators" })
    public void creator() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        String nowStr = now.toString();

        String title = "Java client Blog post";
        String desc = "Test post, sent via Java client at " + nowStr;
        BlogPost post = new BlogPost(title, desc);
        post.setPublishTime(now);
        posts.creator(post).create();
    }

    @Test(dependsOnMethods = { "creator" }, groups = { "updaters" })
    public void updater() {
        BlogPosts posts = conn.blogPosts();

        BlogPost blogPost = fetchRecentBlogPost();
        blogPost.setTitle("Java client was here");
        posts.updater(blogPost).update();
    }

    @Test(dependsOnMethods = { "updater" }, groups = { "deleters" })
    public void deleter() {
        BlogPosts posts = conn.blogPosts();
        BlogPost blogPost = fetchRecentBlogPost();
        posts.deleter(blogPost.id()).delete();
    }

    @Test(groups = { "counters" })
    public void counter() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).count();
    }

    @Test(groups = { "counters" })
    public void counterApproved() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).approved().count();
    }

    @Test(groups = { "counters" })
    public void counterUnpproved() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).unapproved().count();
    }

    @Test(groups = { "counters" })
    public void counterPublic() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).onlyPublic().count();
    }

    @Test(groups = { "admin", "counters" })
    public void counterPrivate() {
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).onlyPrivate().count();
    }

    @Test(groups = { "counters" })
    public void counterForAuthor() {
        String currentAuthor = getCurrentUserAuthorString();
        BlogPosts posts = conn.blogPosts();
        DateTime now = new DateTime();
        posts.counter(now.minusDays(5)).author(currentAuthor).count();
    }

    @Test(groups = { "listers" })
    public void listerForRecent() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentApproved() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.approved();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentUnapproved() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.unapproved();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "admin", "listers" })
    public void listerForRecentPrivate() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.onlyPrivate();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentPublic() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.onlyPublic();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForRecentAuthor() {
        String currentAuthor = getCurrentUserAuthorString();
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.author(currentAuthor);
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeatured() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForFeatured(BlogPostField.featuredDate);
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedApproved() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForFeatured(BlogPostField.featuredDate);
        blogLister = blogLister.approved();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedUnapproved() {
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForFeatured(BlogPostField.featuredDate);
        blogLister = blogLister.unapproved();
        iterateBlogPosts(blogLister);
    }

    @Test(groups = { "listers" })
    public void listerForFeaturedAuthor() {
        String currentAuthor = getCurrentUserAuthorString();
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForFeatured(BlogPostField.featuredDate);
        blogLister = blogLister.author(currentAuthor);
        iterateBlogPosts(blogLister);
    }

    /**
     * Fetch the most recent BlogPost
     *
     * @return The most recent BlogPost
     */
    private BlogPost fetchRecentBlogPost() {
        String currentAuthor = getCurrentUserAuthorString();
        BlogPosts posts = conn.blogPosts();
        Lister blogLister = posts.listerForRecent(BlogPostField.title);
        blogLister = blogLister.author(currentAuthor);
        Iterator<BlogPost> blogIterator = blogLister.iterator();
        assert blogIterator.hasNext() : "Current member has no blog posts to test with";
        BlogPost blogPost = blogIterator.next();

        return blogPost;
    }

    /**
     * Iterate over the given Lister five times
     *
     * @param lister
     *            Lister to iterate over
     */
    private void iterateBlogPosts(Lister lister) {
        int count = 0;
        Iterator<BlogPost> blogIterator = lister.iterator();
        while (blogIterator.hasNext()) {
            blogIterator.next();
            if (count > 5) {
                break;
            } else {
                count += 1;
            }
        }
    }
}
