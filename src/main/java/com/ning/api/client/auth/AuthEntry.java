package com.ning.api.client.auth;

/**
 * Simple container of an authorization property pair; public id part, and
 * confidential token part. Used for multiple use cases
 */
public class AuthEntry
{
    private final String key;
    private final String token;

    public AuthEntry(String key, String token)
    {
        this.key = key;
        this.token = token;
    }

    public String getKey() { return key; }
    public String getToken() { return token; }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("{ key=");
        appendValue(sb, key);
        sb.append(", token=");
        appendValue(sb, token);
        sb.append("}");
        return sb.toString();
    }
    
    private void appendValue(StringBuilder sb, String value)
    {
        if (value == null) {
            sb.append("null");
        } else {
            sb.append('"');
            sb.append(value);
            sb.append('"');
        }
    }
}
