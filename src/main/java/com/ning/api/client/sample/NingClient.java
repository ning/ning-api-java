package com.ning.api.client.sample;

import java.io.IOException;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.http.NingHttpClient;
import com.ning.api.client.http.NingHttpException;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.item.Token;

public class NingClient
{
    /**
     * If not explicitly specified, we will default to using port 8080,
     * which is 
     */
    public final static int XAPI_REGULAR_PORT = 8080;

    public final static int XAPI_SECURE_PORT = 8443;

    public final static String DEFAULT_HOST = "external.ningapis.com";
    
    public final String API_PATH_PREFIX = "/xn/rest/";

    public final String API_VERSION = "/1.0/";

    // For now we'll use 8 second timeout (5 was too low!)
    public final int DEFAULT_TIMEOUT_MSECS = 8000;

    /**
     * Network identifier of Ning network to connect to.
     */
    private final String networkId;

    /**
     * Consumer authorization credentials that Ning has created for
     * the network in question
     */
    private final AuthEntry consumerAuth;

    /**
     * URL prefix for external API request when using non-secure endpoint.
     */
    private final String xapiPrefixRegular;

    /**
     * URL prefix for external API request when using secure endpoint.
     */
    private final String xapiPrefixSecure;

    private final ObjectMapper objectMapper;

    private final NingHttpClient httpClient;
    
    public NingClient(String networkId, AuthEntry consumerAuth)
    {
        this(networkId, consumerAuth, null);
    }
    
    public NingClient(String networkId, AuthEntry consumerAuth, String host)
    {
        this(networkId, consumerAuth, host, -1, -1);
    }

    public NingClient(String networkId, AuthEntry consumerAuth, String host, int port, int securePort)
    {
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
        
        httpClient = new NingHttpClient();
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Public API
    /////////////////////////////////////////////////////////////////////////
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
            /* 25-Aug-2010, tatu: not quite sure why and how quoting is expected
             *    this early for trailing ampersand... but that's how the
             *    cookie crumbles ("it ain't working... that's how you do it!")
             */
//            .addFormParameter("oauth_signature", consumerAuth.getToken()+"&")
            .addFormParameter("oauth_signature", consumerAuth.getToken()+"%26")
            ;
        
        // And then make the call
        NingHttpResponse resp = post.execute(DEFAULT_TIMEOUT_MSECS);

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

    public NingConnection connect(AuthEntry userAuth)
    {
        return new NingConnection(objectMapper, consumerAuth, userAuth, httpClient,
                xapiPrefixRegular, xapiPrefixSecure, DEFAULT_TIMEOUT_MSECS);

    }
}
