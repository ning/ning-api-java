package com.ning.api.client.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Since cloning is not necessarily supported on all platforms, let's wrap
 * synchronization/reuse details here.
 */
public class ThreadSafeHMAC
{
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private final static UTF8Codec utf8Codec = new UTF8Codec();
    
    private final Mac mac;
    
    public ThreadSafeHMAC(ConsumerKey consumerAuth, AuthEntry userAuth)
    {
        byte[] keyBytes = utf8Codec.toUTF8(consumerAuth.getToken() + "&" + userAuth.getToken());
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1_ALGORITHM);
        
        // Get an hmac_sha1 instance and initialize with the signing key
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);        
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        
    }

    public synchronized byte[] digest(byte[] message)
    {
        mac.reset();
        return mac.doFinal(message);
    }
}
