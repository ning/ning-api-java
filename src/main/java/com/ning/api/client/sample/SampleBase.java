package com.ning.api.client.sample;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.auth.ConsumerKey;

public abstract class SampleBase
{
    public final static String DEFAULT_XAPI_HOST = "external.ningapis.com";
    
    // 'www' is used for bootstrapping (listing Networks that user owns)
    public final static String DEFAULT_NETWORK = "www";

    public final static int DEFAULT_HTTP_PORT = 80;
    public final static int DEFAULT_HTTPS_PORT = 443;
    
    // bogus ones: need to externalize

    public final static String TEST_CONSUMER_KEY = "11111111-1111-1111-1111-111111111111";
    public final static String TEST_CONSUMER_SECRET = "11111111-1111-1111-1111-111111111111";

    public final static String TEST_USER_KEY = "11111111-1111-1111-1111-111111111111";
    public final static String TEST_USER_TOKEN = "11111111-1111-1111-1111-111111111111";
    
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
