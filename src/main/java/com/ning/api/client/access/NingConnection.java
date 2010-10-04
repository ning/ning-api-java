package com.ning.api.client.access;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.http.client.SignatureCalculator;

import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.auth.OAuthSignatureCalculator;
import com.ning.api.client.http.NingHttpClient;
import com.ning.api.client.http.NingHttpDelete;
import com.ning.api.client.http.NingHttpGet;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpPut;

/**
 * Class used for actual access; initialized with connection and credentials information
 * to be able to make requests.
 */
public class NingConnection
{

    // // // Helper objects:

    private final NingHttpClient httpClient;

    private final SignatureCalculator signatureCalculator;
    
    /**
     * URL prefix for external API request when using non-secure endpoint.
     */
    private final String xapiPrefixRegular;

    /**
     * URL prefix for external API request when using secure endpoint.
     */
    private final String xapiPrefixSecure;

    private final long defaultTimeoutMsecs;

    // // // Accessors:
    
    private final Activities activities;
    private final BlogPosts blogPosts;
    private final BroadcastMessages broadcastMessages;
    private final Comments comments;
    private final Networks networks;
    private final Photos photos;
    private final Users users;
    private final Videos videos;
    
    public NingConnection(ObjectMapper objectMapper,
            AuthEntry consumerAuth, AuthEntry userAuth,
            NingHttpClient httpClient,
            String xapiPrefixRegular, String xapiPrefixSecure,
            long defaultTimeoutMsecs)
    {
        this.httpClient = httpClient;
        signatureCalculator = new OAuthSignatureCalculator(consumerAuth, userAuth);

        this.xapiPrefixRegular = xapiPrefixRegular;
        this.xapiPrefixSecure = xapiPrefixSecure;
        this.defaultTimeoutMsecs = defaultTimeoutMsecs;

        activities = new Activities(this);
        blogPosts = new BlogPosts(this);
        broadcastMessages = new BroadcastMessages(this);
        comments = new Comments(this);
        networks = new Networks(this);
        photos = new Photos(this);
        users = new Users(this);
        videos = new Videos(this);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, access to accessors
    ///////////////////////////////////////////////////////////////////////
     */

    public Activities activities() { return activities; }
    public BlogPosts blogPosts() { return blogPosts; }
    public BroadcastMessages broadcastMessages() { return broadcastMessages; }
    public Comments comments() { return comments; }
    public Networks networks() { return networks; }
    public Photos photos() { return photos; }
    public Users users() { return users; }
    public Videos videos() { return videos; }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API for accessors, HTTP method construction
    ///////////////////////////////////////////////////////////////////////
     */

    public NingHttpDelete prepareHttpDelete(String endpoint)
    {
        String url = regularPrefixFor(endpoint);
        return httpClient.prepareDelete(url, signatureCalculator);
    }
    
    public NingHttpGet prepareHttpGet(String endpoint)
    {
        String url = regularPrefixFor(endpoint);
        return httpClient.prepareGet(url, signatureCalculator);
    }

    public NingHttpPost prepareHttpPost(String endpoint)
    {
        String url = regularPrefixFor(endpoint);
        return httpClient.preparePost(url, signatureCalculator);
    }

    public NingHttpPut prepareHttpPut(String endpoint)
    {
        String url = regularPrefixFor(endpoint);
        return httpClient.preparePut(url, signatureCalculator);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API for accessors, HTTP method construction
    ///////////////////////////////////////////////////////////////////////
     */

    public long getDefaultTimeoutMsecs() { return defaultTimeoutMsecs; }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Internal methods, factory methods
    ///////////////////////////////////////////////////////////////////////
     */

    protected String regularPrefixFor(String endpoint) {
        return xapiPrefixRegular + endpoint;
    }

    protected String securePrefixFor(String endpoint) {
        return xapiPrefixSecure + endpoint;
    }
}
