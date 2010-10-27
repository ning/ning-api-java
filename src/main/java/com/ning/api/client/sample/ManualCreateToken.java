package com.ning.api.client.sample;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.NingClient;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.item.Token;

/**
 * Temporary class to test functionality from command-line; this one constructing a
 * security token
 */
public class ManualCreateToken
{
    protected final static String USER_EMAIL = "user@email";
    protected final static String USER_PASS = "password";

    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, generate security token:");
        NingClient client = new NingClient(SampleBase.DEFAULT_NETWORK,
                new ConsumerKey(SampleBase.TEST_CONSUMER_KEY, SampleBase.TEST_CONSUMER_SECRET),
                SampleBase.DEFAULT_XAPI_HOST, SampleBase.DEFAULT_HTTP_PORT, SampleBase.DEFAULT_HTTPS_PORT);
        Token token = client.createToken(USER_EMAIL, USER_PASS);

        // for output let's convert back to JSON:
        System.out.println("Call result ("+token.getClass()+") as JSON:");
        System.out.println(new ObjectMapper().writeValueAsString(token));
        System.out.println("And contained auth entry: "+token.asTokenAuthEntry());
        System.out.println("[DONE]");
    }
}
