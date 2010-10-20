package com.ning.api.client.http.jdk;

import java.io.IOException;
import java.io.InputStream;
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
    
    public JdkResponseImpl(ObjectMapper objectMapper, HttpURLConnection connection)
        throws IOException
    {
        super(objectMapper);
        this.connection = connection;
        responseCode = connection.getResponseCode();
    }
    
    @Override
    public String getResponseBody() throws NingTransferException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getStatusCode() {
        return responseCode;
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
