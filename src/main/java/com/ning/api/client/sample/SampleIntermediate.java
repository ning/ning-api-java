package com.ning.api.client.sample;

import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;

/**
 * Intermediate base class just to encapsulate credential info here, for now
 */
public abstract class SampleIntermediate extends SampleBase
{
    protected final static String TEST_NETWORK = "tatutest";

    protected final static String TEST_CONSUMER_KEY = "58ae0fea-ae25-4c3b-b868-ac5591396a9e";
    protected final static String TEST_CONSUMER_SECRET = "85885843-6153-465e-88b5-a1d4f4146d6e";

    protected final static String TEST_USER_KEY = "878ace49-f324-403b-85c9-3d78117147e1";
    protected final static String TEST_USER_TOKEN = "12913470-6dee-4944-8bbc-661401fca07a";
    
    protected SampleIntermediate()
    {
      super("localhost", 9090, 8443, TEST_NETWORK);
    }

    protected AuthEntry getConsumerKey() {
        return new AuthEntry(TEST_CONSUMER_KEY, TEST_CONSUMER_SECRET);
    }

    public AuthEntry getUserToken() {
        return new AuthEntry(TEST_USER_KEY, TEST_USER_TOKEN);
    }
    
    protected abstract void doAction(NingConnection conn) throws Exception;
}
