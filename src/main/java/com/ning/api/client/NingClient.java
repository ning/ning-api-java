package com.ning.api.client;

import java.io.IOException;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.http.NingHttpClient;
import com.ning.api.client.http.NingHttpException;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.http.async.AsyncClientImpl;
import com.ning.api.client.item.Token;

public class NingClient
{
    /*
    ///////////////////////////////////////////////////////////////////////
    // Constants
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * If not explicitly specified, we will default to using port 80.
     */
    public final static int XAPI_REGULAR_PORT = 80;

    public final static int XAPI_SECURE_PORT = 443;

    public final static String DEFAULT_HOST = "external.ningapis.com";
    
    public final String API_PATH_PREFIX = "/xn/rest/";

    public final String API_VERSION = "/1.0/";
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Configuration
    /////////////////////////////////////////////////////////////////////////
     */

    protected NingClientConfig config = NingClientConfig.defaults();
    
    /**
     * Network identifier of Ning network to connect to.
     */
    protected final String networkId;

    /**
     * Consumer authorization credentials that Ning has created for
     * the network in question
     */
    protected final ConsumerKey consumerAuth;

    /**
     * URL prefix for external API request when using non-secure endpoint.
     */
    protected final String xapiPrefixRegular;

    /**
     * URL prefix for external API request when using secure endpoint.
     */
    protected final String xapiPrefixSecure;

    /*
    ///////////////////////////////////////////////////////////////////////
    // Helper objects
    ///////////////////////////////////////////////////////////////////////
     */
    
    private final ObjectMapper objectMapper;

    private final NingHttpClient httpClient;

    /*
    ///////////////////////////////////////////////////////////////////////
    // Construction
    ///////////////////////////////////////////////////////////////////////
     */
    
    public NingClient(String networkId, ConsumerKey consumerAuth)
    {
        this(null, networkId, consumerAuth, null);
    }

    public NingClient(NingHttpClient httpClient,
            String networkId, ConsumerKey consumerAuth)
    {
        this(httpClient, networkId, consumerAuth, null);
    }
    
    public NingClient(String networkId, ConsumerKey consumerAuth, String host)
    {
        this(null, networkId, consumerAuth, host, -1, -1);
    }

    public NingClient(NingHttpClient httpClient, String networkId, ConsumerKey consumerAuth, String host)
    {
        this(httpClient, networkId, consumerAuth, host, -1, -1);
    }
    
    public NingClient(String networkId, ConsumerKey consumerAuth, String host, int port, int securePort)
    {
        this(null, networkId, consumerAuth, host, port, securePort);
    }

    public NingClient(NingHttpClient httpClient,
            String networkId, ConsumerKey consumerAuth, String host, int port, int securePort)
    {
        // Default to using Async HTTP client
        if (httpClient == null) {
            httpClient = constructDefaultHttpClient();
        }
        this.httpClient = httpClient;
        
        this.networkId = networkId;
        this.consumerAuth = consumerAuth;
        if (host == null) {
            host = DEFAULT_HOST;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(host).append(":");
        sb.append((port > 0) ? port : XAPI_REGULAR_PORT);
        sb.append(API_PATH_PREFIX).append(networkId).append(API_VERSION);
        xapiPrefixRegular = sb.toString();

        sb = new StringBuilder();
        sb.append("https://").append(host).append(":");
        sb.append((securePort > 0) ? securePort : XAPI_SECURE_PORT);
        sb.append(API_PATH_PREFIX).append(networkId).append(API_VERSION);
        xapiPrefixSecure = sb.toString();

        objectMapper = new ObjectMapper();
        // let's not barf on unrecognized fields:
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /* By default (unless told otherwise) we will use Ning async http client
     */
    private static NingHttpClient constructDefaultHttpClient() {
        return new AsyncClientImpl();
        // 20-Oct-2010, tatu: Some platforms (read: Android) aren't ok with async-hc; can use JDK-based:
//        return new com.ning.api.client.http.jdk.JdkClientImpl();
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, configuration
    ///////////////////////////////////////////////////////////////////////
     */

    public NingClientConfig getConfig() { return config; }

    /**
     * Method for overriding configuration of this client instance with
     * specified overrides. Resulting configuration will be used as the
     * default configuration for connections created by this client (as well
     * as for operations it directly does).
     */
    public void overrideConfig(NingClientConfig configOverrides) {
        // note: we will merge settings, to ensure there are defaults of some kind
        this.config = config.overrideWith(configOverrides);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, token creation
    ///////////////////////////////////////////////////////////////////////
     */
    
    /**
     * Method for creating new security credentials for given user; using
     * basic authentication and specified password.
     */
    public Token createToken(String user, String userPassword) throws IOException
    {
        // first, we must use secure connection for Basic Auth
        String url = xapiPrefixSecure+"Token";        
        NingHttpPost post = httpClient.preparePost(url, null); // no signature calculation
        post = post.addBasicAuth(user, userPassword);
        post = post.addAccept("*/*");
        
        // and then authorization as "application/x-www-form-urlencoded"
        post = post.addFormParameter("oauth_signature_method","PLAINTEXT")
            .addFormParameter("oauth_consumer_key", consumerAuth.getKey())
            .addFormParameter("oauth_signature", consumerAuth.getSecret()+"&")
            ;
        
        // And then make the call
        NingHttpResponse resp = post.execute(config.getReadTimeoutMsecs());

        int code = resp.getStatusCode();
        if (code != 200) {
            String msg = resp.safeGetResponseBody();
            switch (code) {
            case 401:
                throw new NingHttpException(code,  msg,
                        "Invalid credentials (network '"+networkId+"'); not authorized to create a Token");
            case 500:
            case 503:
            case 504:
                throw new IllegalStateException("Server-side problem (code "+code+"), error message: "+resp.safeGetResponseBody());
            default:
                throw new IllegalStateException("Unrecognized problem (code "+code+"), error message: "+resp.safeGetResponseBody());
            }
        }
        
        // should we catch exception? For now seems best to let through
        Token tokenResp = resp.asSingleItem(Token.class);
        return tokenResp;
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API, creating connection to access resources
    ///////////////////////////////////////////////////////////////////////
     */
    
    /**
     * Method for creating {@link NingConnection} to access resources using
     * specified User token, and default configuration settings.
     */
    public NingConnection connect(Token token)
    {
        return connect(token, null);
    }

    public NingConnection connect(Token token, NingClientConfig override)
    {
        return connect(new RequestToken(token.getOauthToken(), token.getOauthTokenSecret()),
                override);
    }
    
    /**
     * Method for creating {@link NingConnection} to access resources using
     * specified User token, and default configuration settings.
     */
    public NingConnection connect(RequestToken userAuth)
    {
        return connect(userAuth, null);
    }
    
    /**
     * Method for creating {@link NingConnection} to access resources using
     * specified User token, and specified configuration overrides (above
     * and beyond default client configuration settings)
     */
    public NingConnection connect(RequestToken userAuth, NingClientConfig override)
    {
        NingClientConfig connectionConfig = config.overrideWith(override);
        return new NingConnection(connectionConfig, objectMapper, consumerAuth, userAuth, httpClient,
                xapiPrefixRegular, xapiPrefixSecure);

    }
}
