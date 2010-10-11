package com.ning.api.client.access;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.http.client.SignatureCalculator;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.auth.ConsumerKey;
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

    protected final NingHttpClient httpClient;

    protected final SignatureCalculator signatureCalculator;
    
    /**
     * URL prefix for external API request when using non-secure end point.
     */
    protected final String xapiPrefixRegular;

    /**
     * URL prefix for external API request when using secure end point.
     */
    protected final String xapiPrefixSecure;

    /**
     * Configuration of connection settings for this connection
     */
    protected NingClientConfig config;
    
    public NingConnection(NingClientConfig config,
            ObjectMapper objectMapper,
            ConsumerKey consumerAuth, RequestToken userAuth,
            NingHttpClient httpClient,
            String xapiPrefixRegular, String xapiPrefixSecure)
    {
        this.httpClient = httpClient;
        signatureCalculator = new OAuthSignatureCalculator(consumerAuth, userAuth);

        this.xapiPrefixRegular = xapiPrefixRegular;
        this.xapiPrefixSecure = xapiPrefixSecure;
        this.config = config;
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: configuration
    ///////////////////////////////////////////////////////////////////////
     */

    public NingClientConfig getConfig() { return config; }

    /**
     * Method for overriding configuration of this connection object with
     * specified overrides. Resulting configuration will be used as the
     * default configuration for accessors.
     */
    public void overrideConfig(NingClientConfig configOverrides) {
        // note: we will merge settings, to ensure there are defaults of some kind
        this.config = config.overrideWith(configOverrides);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, access to accessors
    ///////////////////////////////////////////////////////////////////////
     */

    public Activities activities() { return new Activities(this, config); }
    public BlogPosts blogPosts() { return new BlogPosts(this, config); }
    public BroadcastMessages broadcastMessages() { return new BroadcastMessages(this, config); }
    public Comments comments() { return new Comments(this, config); }
    public Networks networks() { return new Networks(this, config); }
    public Photos photos() { return new Photos(this, config); }
    public Users users() { return new Users(this, config); }
    public Videos videos() { return new Videos(this, config); }
    
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
