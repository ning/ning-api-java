package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.RequestToken;
import com.ning.api.client.auth.ConsumerKey;

/**
 * Intermediate base class just to encapsulate credential info here, for now
 */
public abstract class SampleIntermediate extends SampleBase
{
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

    @Override
    protected ConsumerKey getConsumerKey() {
        return new ConsumerKey(TEST_CONSUMER_KEY, TEST_CONSUMER_SECRET);
    }

    public RequestToken getUserToken() {
        return new RequestToken(TEST_USER_KEY, TEST_USER_TOKEN);
    }
    
    protected abstract void doAction(NingConnection conn) throws Exception;
}
