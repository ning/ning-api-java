package com.ning.api.client.sample;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.NingClient;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.item.Token;

/**
 * Temporary class to test functionality from command-line; this one constructing a
 * security token
 */
public class ManualTestToken
{
	protected final static String TEST_NETWORK = "www";
	
    protected final static String TEST_CONSUMER_KEY = "11111111-1111-1111-1111-111111111111";
    protected final static String TEST_CONSUMER_SECRET = "11111111-1111-1111-1111-111111111111";
    
    protected final static String USER_EMAIL = "user@email";
    protected final static String USER_PASS = "password";

    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, generate security token:");
        NingClient client = new NingClient(TEST_NETWORK,
                new ConsumerKey(TEST_CONSUMER_KEY, TEST_CONSUMER_SECRET),
                SampleBase.DEFAULT_XAPI_HOST, 80, 443);
        Token token = client.createToken(USER_EMAIL, USER_PASS);

        // for output let's convert back to JSON:
        System.out.println("Call result ("+token.getClass()+") as JSON:");
        System.out.println(new ObjectMapper().writeValueAsString(token));
        System.out.println("And contained auth entry: "+token.asTokenAuthEntry());
        System.out.println("[DONE]");
    }
}
