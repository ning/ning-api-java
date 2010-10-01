package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;

import com.ning.api.client.auth.AuthEntry;

/**
 *<p>
 * Note: Tokens are considered ContentItems, since access is separate.
 */
public class Token
{
    @JsonProperty
    protected String oauthConsumerKey;

    @JsonProperty
    protected String oauthToken;

    @JsonProperty
    protected String oauthTokenSecret;
    
    public Token() { }

    public String getOauthConsumerKey() { return oauthConsumerKey; }
    public String getOauthToken() { return oauthToken; }
    public String getOauthTokenSecret() { return oauthTokenSecret; }

    /**
     * Convenience method for accessing newly created token information.
     * Note that if either 'token' or 'tokenSecret' is missing, will throw
     * {@link IllegalStateException}.
     */
    public AuthEntry asTokenAuthEntry()
    {
        if (oauthToken == null) {
            throw new IllegalStateException("oauthToken property null");
        }
        if (oauthTokenSecret== null) {
            throw new IllegalStateException("oauthTokenSecret property null");
        }
        return new AuthEntry(oauthToken, oauthTokenSecret);
    }
}
