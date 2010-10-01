package com.ning.api.client.sample;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.auth.AuthEntry;
import com.ning.api.client.item.Token;

/**
 * Temporary class to test functionality from command-line; this one constructing a
 * security token
 */
public class ManualTestToken
{
    public static void main(String[] args) throws Exception
    {
        System.out.println("About to construct client, generate security token:");
        NingClient client = new NingClient("tatutest",
                new AuthEntry("58ae0fea-ae25-4c3b-b868-ac5591396a9e", "85885843-6153-465e-88b5-a1d4f4146d6e"),
                "localhost", 9090, 8443);
        Token token = client.createToken("ningdev@ning.com", "foo");

        // for output let's convert back to JSON:
        System.out.println("Call result ("+token.getClass()+") as JSON:");
        System.out.println(new ObjectMapper().writeValueAsString(token));
        System.out.println("And contained auth entry: "+token.asTokenAuthEntry());
        System.out.println("[DONE]");
    }
}
