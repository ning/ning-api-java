package com.ning.api.client.tests;

import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.NingClient;
import com.ning.api.client.item.Token;

public class TokenIT extends BaseIT {
    @Parameters({ "xapi-host", "http-port", "https-port", "subdomain",
            "consumer-key", "consumer-secret", "user-email", "user-password" })
    public TokenIT(String xapiHost, int defaultHttpPort, int DefaultHttpsPort,
            String subdomain, String consumerKey, String consumerSecret,
            String userEmail, String userPassword) {
        super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
                consumerKey, consumerSecret, userEmail, userPassword);
    }

    @Test(groups = { "creators" })
    public void testTokenReuse() throws IOException {
        NingClient client = getNingClient();
        Token firstToken = client
                .createToken(this.userEmail, this.userPassword);
        Token secondToken = client.createToken(this.userEmail,
                this.userPassword);
        Assert.assertEquals(firstToken.getOauthConsumerKey(),
                secondToken.getOauthConsumerKey());
        Assert.assertEquals(firstToken.getOauthToken(),
                secondToken.getOauthToken());
        Assert.assertEquals(firstToken.getOauthTokenSecret(),
                secondToken.getOauthTokenSecret());
    }

}
