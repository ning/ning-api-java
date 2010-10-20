package com.ning.api.client.http.jdk;

import java.io.*;
import java.net.HttpURLConnection;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.ning.api.client.exception.NingTransferException;
import com.ning.api.client.exception.NingTransformException;
import com.ning.api.client.http.NingHttpResponse;

public class JdkResponseImpl extends NingHttpResponse
{
    protected final HttpURLConnection connection;

    protected final int responseCode;

    private String responseBody;
    
    public JdkResponseImpl(ObjectMapper objectMapper, HttpURLConnection connection)
        throws IOException
    {
        super(objectMapper);
        this.connection = connection;
        responseCode = connection.getResponseCode();
    }
    
    @Override
    public String getResponseBody() throws NingTransferException {
        /* JDK's HttpURLConnection is a PoS, and requires separate handling
         * for ok and error use cases... so
         * 
         */
        if (responseBody == null) {
            InputStream in = null;
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1000];
            try {
                if (isError()) {
                    in = connection.getErrorStream();
                } else {
                    in = connection.getInputStream();
                }
                
                InputStreamReader r = new InputStreamReader(in, "UTF-8");
                int count;
                while ((count = r.read(buffer)) > 0) {
                    sb.append(buffer, 0, count);
                }
                r.close();
            } catch (IOException ioe) {
                throw new NingTransferException(ioe);
            }
            responseBody = sb.toString();
        }
        return responseBody;
    }

    @Override
    public int getStatusCode() {
        return responseCode;
    }

    protected boolean isError() {
        int code = getStatusCode();
        return code < 200 || code >= 300;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T> T readAndBind(JavaType valueType)
    {
        verifyResponse();
        try {
            InputStream in = connection.getInputStream();
            Object ob = objectMapper.readValue(in, valueType);
            // note: mapper by default closes underlying input stream automatically
            return (T) ob;
        } catch (JsonProcessingException e) {
            throw new NingTransformException("Failed to bind JSON into type "+valueType+": "+e.getMessage(), e);
        } catch (IOException e) {
            throw new NingTransferException("Failed to read data (of assumed type "+valueType+"): "+e.getMessage(), e);
        }    
    }

}
