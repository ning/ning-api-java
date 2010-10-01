package com.ning.api.client.auth;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUTF8UrlCodec
{
    private final UTF8UrlCodec codec = new UTF8UrlCodec();
    
    @Test(groups="fast")
    public void testBasics()
    {
        Assert.assertEquals(codec.encode("foobar"), "foobar");
        Assert.assertEquals(codec.encode("a&b"), "a%26b");
        Assert.assertEquals(codec.encode("a+b"), "a%2Bb");
    }
}
