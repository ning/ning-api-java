package com.ning.api.client.http;

import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;

/**
 * Intermediate base class for PUT and POST methods
 */
public class NingHttpRequestWithBody<T extends NingHttpRequest<T>>
    extends NingHttpRequest<T>
{
    protected String explicitBody;
    
    protected NingHttpRequestWithBody(BoundRequestBuilder rawRequest)
    {
        super(rawRequest);
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Overridden methods
    /////////////////////////////////////////////////////////////////////////
    */
    
    protected void completeRequestBeforeExecute()
    {
        if (explicitBody != null) {
            requestBuilder = requestBuilder.setBody(explicitBody);
        }
        super.completeRequestBeforeExecute();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected T _this() {
        return (T) this;
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Body modification methods
    /////////////////////////////////////////////////////////////////////////
    */

    public T addFormParameter(String key, String value)
    {
        requestBuilder = requestBuilder.addParameter(key, value);
        return _this();
    }

    public T addFormParameter(Param p)
    {
        if (p != null) {
            addFormParameter(p.name, p.value);
        }
        return _this();
    }

    public T addFormParameters(Param... params)
    {
        if (params != null) {
            for (Param p : params) {
                addFormParameter(p);
            }
        }
        return _this();
    }
    
    public T setBody(String content, String contentType)
    {
        explicitBody = content;
        this.contentType = contentType;
        return _this();
    }
}
