package com.ning.api.client.http.async;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import com.ning.api.client.exception.NingTransferException;
import com.ning.api.client.exception.NingTransformException;
import com.ning.api.client.http.NingHttpResponse;
import com.ning.http.client.Response;

public class AsyncResponseImpl extends NingHttpResponse
{
    /**
     * Actual low-level response object that contains data to process.
     */
    private final Response rawResponse;

    private String responseBody;

    public AsyncResponseImpl(ObjectMapper objectMapper, Response rawResponse)
    {
        super(objectMapper);
        this.rawResponse = rawResponse;
    }

    @Override
    public int getStatusCode() { return rawResponse.getStatusCode(); }

    @Override
    public String getResponseBody() throws NingTransferException
    {
        if (responseBody == null) {
            try {
                responseBody = rawResponse.getResponseBody();
            } catch (IOException ioe) {
                throw new NingTransferException(ioe);
            }
        }
        return responseBody;
    }

    @SuppressWarnings("unchecked")
    protected <T> T readAndBind(JavaType valueType)
    {
        verifyResponse();
        try {
            InputStream in = rawResponse.getResponseBodyAsStream();
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
