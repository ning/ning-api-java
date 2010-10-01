package com.ning.api.client.auth;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ning.http.client.FluentStringsMap;

public class TestSignatureCalculator
{
    private static final String CONSUMER_KEY = "dpf43f3p2l4k3l03";

    private static final String CONSUMER_SECRET = "kd94hf93k423kf44";

    public static final String TOKEN_KEY = "nnch734d00sl2jdk";

    public static final String TOKEN_SECRET = "pfkkdhi9sl3r4s00";

    public static final String NONCE = "kllo9940pd9333jh";

    final static long TIMESTAMP = 1191242096;
    
    @Test(groups="fast")
    public void test()
    {
//        Assert.assertEquals(codec.encode("foobar"), "foobar");

        AuthEntry consumer = new AuthEntry(CONSUMER_KEY, CONSUMER_SECRET);
        AuthEntry user = new AuthEntry(TOKEN_KEY, TOKEN_SECRET);
        OAuthSignatureCalculator calc = new OAuthSignatureCalculator(consumer, user);
        /*
    public String calculateSignature(String method, String baseURL, long oauthTimestamp, String nonce,
            FluentStringsMap formParams, FluentStringsMap queryParams)
*/
        FluentStringsMap queryParams = new FluentStringsMap();
        queryParams.add("file", "vacation.jpg");
        queryParams.add("size", "original");
        String url = "http://photos.example.net/photos";
        String sig = calc.calculateSignature("GET", url, TIMESTAMP, NONCE, null, queryParams);

        Assert.assertEquals("tR3+Ty81lMeYAr/Fid0kMTYa/WM=", sig);
        
        // based on the reference test case from
        // http://oauth.pbwiki.com/TestCases
        /*
        OAuthMessageSigner signer = new HmacSha1MessageSigner();
        signer.setConsumerSecret(CONSUMER_SECRET);
        signer.setTokenSecret(TOKEN_SECRET);

        HttpRequest request = mock(HttpRequest.class);
        when(request.getRequestUrl()).thenReturn();
        when(request.getMethod()).thenReturn("GET");

        HttpParameters params = new HttpParameters();
        params.putAll(OAUTH_PARAMS);
*/
    }
}
