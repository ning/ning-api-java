package com.ning.api.client.http.jdk;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ning.http.client.FluentStringsMap;
import com.ning.http.util.UTF8UrlEncoder;

import org.codehaus.jackson.map.ObjectMapper;

import com.ning.api.client.auth.OAuthSignatureCalculator;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.api.client.http.NingRequestBuilder;

public class JdkRequestBuilderImpl extends NingRequestBuilder<JdkRequestBuilderImpl>
{
    protected final String baseURL;
    
    protected final String httpMethod;

    protected final OAuthSignatureCalculator signatureCalculator;
    
    /**
     * We will need to buffer header values for a bit until we have URL connection.
     */
    protected ArrayList<String> headers;
    
    protected FluentStringsMap queryParameters;

    protected FluentStringsMap formParameters;

    protected String body;

    public JdkRequestBuilderImpl(String baseURL, OAuthSignatureCalculator sig, String httpMethod)
    {
        this.baseURL = baseURL;
        this.httpMethod = httpMethod;
        signatureCalculator = sig;
    }
    
    @Override
    public JdkRequestBuilderImpl addHeader(String name, String value)
    {
        if (headers == null) {
            headers = new ArrayList<String>();
        }
        headers.add(name);
        headers.add(value);
        return this;
    }

    @Override
    public JdkRequestBuilderImpl addQueryParameter(String name, String value)
    {
        if (queryParameters == null) {
            queryParameters = new FluentStringsMap();
        }
        queryParameters.add(name, value);
        return this;
    }

    @Override
    public JdkRequestBuilderImpl addFormParameter(String name, String value)
    {
        if (formParameters == null) {
            formParameters = new FluentStringsMap();
        }
        formParameters.add(name, value);
        return this;
    }

    @Override
    public JdkRequestBuilderImpl setBody(String body) {
        this.body = body;
        return this;
    }
    
    @Override
    public Future<NingHttpResponse> sendRequest(ObjectMapper objectMapper)
        throws IOException
    {
        // First, create with query parameters and all
        URL url = buildURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setAllowUserInteraction(false);
        // connection and read timeout; set both to same (meaning it's not quite as strict as end-to-end)
        conn.setDefaultUseCaches(false); // nope, no caching on client side
        conn.setUseCaches(false);
        conn.setDoOutput((body != null) || (formParameters != null));
        conn.setDoInput(true);
        conn.setRequestMethod(httpMethod);

        // Then calculate signature (if necessary), add as header
        if (signatureCalculator != null) {
            String auth = signatureCalculator.calculateAuthorizationHeader(httpMethod,
                    baseURL, formParameters, queryParameters);
            addHeader(OAuthSignatureCalculator.HEADER_AUTHORIZATION, auth);
        }
        
        String bodyToUse = body;
        
        if (bodyToUse == null && formParameters != null) {
            StringBuilder sb = new StringBuilder(100);
            for (Map.Entry<String, List<String>> entry : formParameters) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    if (sb.length() > 0) {
                        sb.append('&');
                    }
                    sb = UTF8UrlEncoder.appendEncoded(sb, key);
                    sb.append('=');
                    sb = UTF8UrlEncoder.appendEncoded(sb, value);
                }
            }
            bodyToUse = sb.toString();
        }
        return new RequestFuture(objectMapper, conn, headers, bodyToUse);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Internal methods
    ///////////////////////////////////////////////////////////////////////
     */

    public URL buildURL() throws IOException
    {
        String url = baseURL;
        if (queryParameters != null) {
            StringBuilder sb = new StringBuilder(80);
            sb.append(url);
            int count = 0;
            for (Map.Entry<String, List<String>> entry : queryParameters) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    if (++count > 1) {
                        sb.append('&');
                    } else {
                        sb.append('?');
                    }
                    sb = UTF8UrlEncoder.appendEncoded(sb, key);
                    sb.append('=');
                    sb = UTF8UrlEncoder.appendEncoded(sb, value);
                }
            }
            url = sb.toString();
        }
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            // Ugh: chained constructor only added in 1.6, so:
            IOException ie = new IOException("Failed to construct URL from '"+url+"': "+e.getMessage());
            ie.initCause(e);
            throw ie;
        }
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Helper class
    ///////////////////////////////////////////////////////////////////////
     */

    protected static class RequestFuture implements Future<NingHttpResponse>
    {
        protected final ObjectMapper objectMapper;

        protected final HttpURLConnection connection;

        protected ArrayList<String> headers;
        
        protected final String body;
        
        public RequestFuture(ObjectMapper objectMapper,
                HttpURLConnection connection,  ArrayList<String> headers,
                String body)
        {
            this.objectMapper = objectMapper;
            this.connection = connection;
            this.headers = headers;
            this.body = body;
        }

        //@Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            // NOP
            return false;
        }

        //@Override
        public NingHttpResponse get() throws InterruptedException, ExecutionException {
            try {
                return completeRequest();
            } catch (IOException e) {
                throw new ExecutionException(e);
            }
        }

        //@Override
        public NingHttpResponse get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException
        {
            if (unit != TimeUnit.MILLISECONDS) {
                timeout = TimeUnit.MILLISECONDS.convert(timeout, unit);
            }
            connection.setConnectTimeout((int)timeout);
            connection.setReadTimeout((int)timeout);
            try {
                return completeRequest();
            } catch (IOException e) {
                throw new ExecutionException(e);
            }
        }

        protected JdkResponseImpl completeRequest() throws IOException
        {
            // Then add explicit headers (if any)
            if (headers != null) {
                for (int i = 0, len = headers.size(); i < len; i += 2) {
                    String name = headers.get(i);
                    String value = headers.get(i+1);
                    connection.addRequestProperty(name, value);
                }
            }
            // Then add body, if necessary
            if (body != null) {
                OutputStream out = connection.getOutputStream();
                Writer w = new OutputStreamWriter(out, "UTF-8");
                w.write(body);
                w.close();
            }
            return new JdkResponseImpl(objectMapper, connection);
        }

        //@Override
        public boolean isCancelled() {
            return false;
        }

        //@Override
        public boolean isDone() {
            return false;
        }
    }        
}
