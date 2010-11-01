package com.ning.api.client.sample;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.auth.ConsumerKey;

public abstract class SampleBase
{
//    public final static String DEFAULT_XAPI_HOST = "external.ningapis.com";
//    public final static String DEFAULT_XAPI_HOST = "z200469.ningops.com";
    public final static String DEFAULT_XAPI_HOST = "localhost";
    
    // 'www' is used for bootstrapping (listing Networks that user owns)
//    public final static String DEFAULT_NETWORK = "www";
    public final static String DEFAULT_NETWORK = "tatutest";
//  public final static String DEFAULT_NETWORK = "bigplastic";

//    public final static int DEFAULT_HTTP_PORT = 80;
//    public final static int DEFAULT_HTTPS_PORT = 443;
//    public final static int DEFAULT_HTTP_PORT = 8080;
//    public final static int DEFAULT_HTTPS_PORT = 8443;
    public final static int DEFAULT_HTTP_PORT = 9090;
    public final static int DEFAULT_HTTPS_PORT = 8443;
    
    // bogus ones: need to externalize

    /*
    public final static String TEST_CONSUMER_KEY = "11111111-1111-1111-1111-111111111111";
    public final static String TEST_CONSUMER_SECRET = "11111111-1111-1111-1111-111111111111";

    public final static String TEST_USER_KEY = "11111111-1111-1111-1111-111111111111";
    public final static String TEST_USER_TOKEN = "11111111-1111-1111-1111-111111111111";
    */

    // XNA
    public final static String TEST_CONSUMER_KEY =    "58ae0fea-ae25-4c3b-b868-ac5591396a9e";
    public final static String TEST_CONSUMER_SECRET = "85885843-6153-465e-88b5-a1d4f4146d6e";

    //public final static String TEST_USER_KEY =   "797f3638-8f63-491b-9cac-d97b38cbc5ba";
    //public final static String TEST_USER_TOKEN = "5554ebf5-5039-448c-a591-65c119905e0b";

    public final static String TEST_USER_KEY =   "878ace49-f324-403b-85c9-3d78117147e1";
    public final static String TEST_USER_TOKEN = "12913470-6dee-4944-8bbc-661401fca07a";

    // Prod; 'bigplastic'
    /*
    public final static String TEST_CONSUMER_KEY =    "cfa8afe9-7cb2-4b7a-811a-b36c6ac27283";
    public final static String TEST_CONSUMER_SECRET = "d4efabe7-cc38-4342-9a2d-a925a2f4e232";

    public final static String TEST_USER_KEY =   "156dc875-08b9-4620-ae24-770a986a0505";
    public final static String TEST_USER_TOKEN = "54eeaacf-5d7b-49be-ba17-53801f6a719e";
    */
    
    protected final String host;
    protected final int httpPort;
    protected final int httpsPort;

    protected final String network;
    
    protected SampleBase(String host, int httpPort, int httpsPort,
            String network)
    {
        this.host = host;
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;

        this.network = network;
    }

    protected abstract ConsumerKey getConsumerKey();
    protected abstract RequestToken getUserToken();

    public void action() throws Exception
    {
        NingClient client = new NingClient(network, getConsumerKey(), host, httpPort, httpsPort);
        NingConnection conn = client.connect(getUserToken());
        this.doAction(conn);
    }
    
    protected abstract void doAction(NingConnection conn) throws Exception;
}
