package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;

/**
 * Intermediate base class just to encapsulate credential info here, for now
 */
public abstract class SampleIntermediate extends SampleBase
{
    public final static String DEFAULT_XAPI_HOST = "external.ningapis.com";
    
    // 'www' is used for bootstrapping (listing Networks that user owns)
    protected final static String DEFAULT_NETWORK = "www";

    // bogus ones: need to externalize

    protected final static String TEST_CONSUMER_KEY = "11111111-1111-1111-1111-111111111111";
    protected final static String TEST_CONSUMER_SECRET = "11111111-1111-1111-1111-111111111111";

    protected final static String TEST_USER_KEY = "11111111-1111-1111-1111-111111111111";
    protected final static String TEST_USER_TOKEN = "11111111-1111-1111-1111-111111111111";
    
    protected SampleIntermediate()
    {
        this(DEFAULT_NETWORK);
    }

    protected SampleIntermediate(String network)
    {
        this(DEFAULT_XAPI_HOST, 80, 443, network);
    }

    protected SampleIntermediate(String hostname, int httpPort, int httpsPort, String network)
    {
      super(hostname, httpPort, httpsPort, network);
    }
    
    protected AuthEntry getConsumerKey() {
        return new AuthEntry(TEST_CONSUMER_KEY, TEST_CONSUMER_SECRET);
    }

    public AuthEntry getUserToken() {
        return new AuthEntry(TEST_USER_KEY, TEST_USER_TOKEN);
    }
    
    protected abstract void doAction(NingConnection conn) throws Exception;
}
