package com.ning.api.client.access;

import org.codehaus.jackson.map.ObjectMapper;

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
 *<p>
 * Connection instances are immutable so they can be shared between threads; new instances
 * (with alternate configuration) can be constructed using {@link #configuredConnection}
 * method. The only exception to immutability is {@link NingHttpClient} object
 * connection has; since it has state, connections are not completely stateless,
 * specifically if and when connection is closed.
 */
public class NingConnection
{
    // // // Helper objects:

    protected final NingHttpClient httpClient;

    protected final ObjectMapper objectMapper;
    
    protected final OAuthSignatureCalculator signatureCalculator;
    
    /**
     * URL prefix for external API request when using non-secure end point.
     */
    protected final String xapiPrefixRegular;

    /**
     * URL prefix for external API request when using secure end point.
     */
    protected final String xapiPrefixSecure;

    /**
     * Configuration of connection settings for this connection; passed to accessor
     * objects constructed from connection (like {@link #activities}).
     */
    protected final NingClientConfig config;

    /**
     * The only mutable state; indicates whether connection has been closed or not
     */
    protected boolean hasBeenClosed = false;
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Life-cycle: creation, disposal
    ///////////////////////////////////////////////////////////////////////
     */
    
    /**
     * Regular constructor used by {@link com.ning.api.client.NingClient} for constructing connection
     * instances.
     */
    public NingConnection(NingClientConfig config,
            ObjectMapper objectMapper,
            ConsumerKey consumerAuth, RequestToken userAuth,
            NingHttpClient httpClient,
            String xapiPrefixRegular, String xapiPrefixSecure)
    {
        this.config = config;
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        signatureCalculator = new OAuthSignatureCalculator(consumerAuth, userAuth);

        this.xapiPrefixRegular = xapiPrefixRegular;
        this.xapiPrefixSecure = xapiPrefixSecure;
    }

    /**
     * Internal "copy constructor" used to create an instance with different
     * configuration
     */
    protected NingConnection(NingConnection baseline, NingClientConfig configOverrides)
    {
        // note: we will merge settings, to ensure there are defaults of some kind
        this.config = baseline.config.overrideWith(configOverrides);
        this.objectMapper = baseline.objectMapper;
        this.httpClient = baseline.httpClient;
        this.signatureCalculator = baseline.signatureCalculator;
        this.xapiPrefixRegular = baseline.xapiPrefixRegular;
        this.xapiPrefixSecure = baseline.xapiPrefixSecure;
    }

    /**
     * Method for creating a new connection object that uses configuration overrides
     * passed in. Note that this connection is not changed in any way; only the new
     * instance has altered configuration.
     */
    public NingConnection configuredConnection(NingClientConfig configOverrides) {
        return new NingConnection(this, configOverrides);
    }

    /**
     * Method that will close any resources (underlying HTTP connection(s))
     * this connection object controls. After calling this method,
     * no
     */
    public void close()
    {
        if (!hasBeenClosed) {
            httpClient.close();
            hasBeenClosed = true;
        }
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: configuration
    ///////////////////////////////////////////////////////////////////////
     */

    public NingClientConfig getConfig() { return config; }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, access to accessors
    ///////////////////////////////////////////////////////////////////////
     */

    public Activities activities() {
        checkNotClosed();
        return new Activities(this, config);
    }

    public BlogPosts blogPosts() {
        checkNotClosed();
        return new BlogPosts(this, config);
    }
    public BroadcastMessages broadcastMessages() {
        checkNotClosed();
        return new BroadcastMessages(this, config);
    }
    public Categories categories() {
        checkNotClosed();
        return new Categories(this, config);
    }
    public Comments comments() {
        checkNotClosed();
        return new Comments(this, config);
    }
    public Networks networks() {
        checkNotClosed();
        return new Networks(this, config);
    }
    public Photos photos() {
        checkNotClosed();
        return new Photos(this, config);
    }
    public Users users() {
        checkNotClosed();
        return new Users(this, config);
    }
    public Videos videos() {
        checkNotClosed();
        return new Videos(this, config);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API for accessors, HTTP method construction
    ///////////////////////////////////////////////////////////////////////
     */

    public NingHttpDelete prepareHttpDelete(String endpoint, NingClientConfig config)
    {
        checkNotClosed();
        String url = prefixFor(endpoint, config);
        return httpClient.prepareDelete(url, signatureCalculator);
    }
    
    public NingHttpGet prepareHttpGet(String endpoint, NingClientConfig config)
    {
        checkNotClosed();
        String url = prefixFor(endpoint, config);
        return httpClient.prepareGet(url, signatureCalculator);
    }

    public NingHttpPost prepareHttpPost(String endpoint, NingClientConfig config)
    {
        checkNotClosed();
        String url = prefixFor(endpoint, config);
        return httpClient.preparePost(url, signatureCalculator);
    }

    public NingHttpPut prepareHttpPut(String endpoint, NingClientConfig config)
    {
        checkNotClosed();
        String url = prefixFor(endpoint, config);
        return httpClient.preparePut(url, signatureCalculator);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Internal methods, factory methods
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Method for constructing URL for given end point, using configuration to
     * determine whether to use secure (https/ssl) or regular (http) connection.
     */
    protected String prefixFor(String endpoint, NingClientConfig config)
    {
        Boolean secure = config.getUseSecureConnection();
        if (secure && secure.booleanValue()) {
            return securePrefixFor(endpoint);
        }
        return regularPrefixFor(endpoint);
    }

    private String regularPrefixFor(String endpoint) {
        return xapiPrefixRegular + endpoint;
    }

    private String securePrefixFor(String endpoint) {
        return xapiPrefixSecure + endpoint;
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Internal methods, other
    ///////////////////////////////////////////////////////////////////////
     */
    
    private void checkNotClosed() {
        if (hasBeenClosed) {
            throw new IllegalStateException("Connection instance closed: can not use");
        }
    }

}
