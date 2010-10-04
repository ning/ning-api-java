package com.ning.api.client.http;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import com.ning.http.client.Response;

import com.ning.api.client.NingClientException;
import com.ning.api.client.access.impl.AnchorHolder;
import com.ning.api.client.exception.NingTransferException;
import com.ning.api.client.exception.NingTransformException;
import com.ning.api.client.item.ContentItem;
import com.ning.api.client.json.ItemCountResponse;
import com.ning.api.client.json.ItemResponse;
import com.ning.api.client.json.ItemSequenceResponse;

/**
 * Wrapper around "raw" HTTP response object; adds convenience accesors
 * as well as basic data binding for JSON content.
 */
public class NingHttpResponse
{
    private final int MAX_ERROR_RESP_TO_INCLUDE = 1000;

    /**
     * Mapper we use for binding data from responses into POJOs
     */
    private final ObjectMapper objectMapper;
    
    /**
     * Actual low-level response object that contains data to process.
     */
    private final Response rawResponse;

    private String responseBody;
    
    public NingHttpResponse(ObjectMapper objectMapper, Response rawResponse)
    {
        this.objectMapper = objectMapper;
        this.rawResponse = rawResponse;
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Simple accessors
    /////////////////////////////////////////////////////////////////////////
     */

    public Response rawResponse() { return rawResponse; }
    
    public int getStatusCode() { return rawResponse.getStatusCode(); }

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

    public String safeGetResponseBody() throws IOException
    {
        try {
            return getResponseBody();
        } catch (NingTransferException e) {
            return "[ERROR fetching response]";
        }
    }

    protected String getTruncatedResponse()
    {
        /*
        InputStream in = response.rawResponse().getResponseBodyAsStream();
        InputStreamReader r = new InputStreamReader(in, "UTF-8");
        char[] buffer = new char[4000];
        int count;
        StringWriter w = new StringWriter();
        while ((count = r.read(buffer)) > 0) {
            w.write(buffer, 0, count);
        }
        String msg = w.toString();
        */

        String msg;
        try {
            msg = getResponseBody();
        } catch (Exception e) {
            return "[failed to access response body, problem: "+e+"]";
        }
        if (msg.isEmpty()) {
            return "[no response message]";
        }
        if (msg.length() > MAX_ERROR_RESP_TO_INCLUDE) {
            return msg.substring(0, MAX_ERROR_RESP_TO_INCLUDE/2) +"'...[TRUNCATED]...'"
                +msg.substring(msg.length() - MAX_ERROR_RESP_TO_INCLUDE);
        }
        return msg;
    }

    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Response data binding
    /////////////////////////////////////////////////////////////////////////
     */
    
    /**
     * Accessor to call when request is assumed to be a single-item request
     * of some sort.
     * 
     * @param <T> Type of single entry item in response
     * @throws IOException
     */
    public <T> T asSingleItem(Class<T> itemClass)
        throws NingClientException
    { 
        verifyResponse();
        ItemResponse<T> response = readAndBind(rawResponse,
                TypeFactory.parametricType(ItemResponse.class, itemClass));
        return response.getEntry();
    }

    /**
     * Accessor to call when request is assumed to be an item list request
     * of some sort.
     * 
     * @param <T> Type of single entry item in response
     * @throws IOException
     */
    public <T extends ContentItem<?,?>> List<T> asItemList(Class<T> itemClass, AnchorHolder anchor)
        throws NingClientException
    {
        verifyResponse();
        ItemSequenceResponse<T> response = readAndBind(rawResponse,
                TypeFactory.parametricType(ItemSequenceResponse.class, itemClass));
        if (anchor != null) {
            anchor.setAnchor(response.getAnchor());
        }
        return response.getEntry();
    }

    public Integer asCount() throws NingClientException
    {
        verifyResponse();
        ItemCountResponse response = readAndBind(rawResponse, TypeFactory.type(ItemCountResponse.class));
        return response.getCount();
    }

    @SuppressWarnings("unchecked")
    protected <T> T readAndBind(Response response, JavaType valueType)
    {
        verifyResponse();
        try {
            InputStream in = response.getResponseBodyAsStream();
            Object ob = objectMapper.readValue(in, valueType);
            // note: mapper by default closes underlying input stream automatically
            return (T) ob;
        } catch (JsonProcessingException e) {
            throw new NingTransformException("Failed to bind JSON into type "+valueType+": "+e.getMessage(), e);
        } catch (IOException e) {
            throw new NingTransferException("Failed to read data (of assumed type "+valueType+"): "+e.getMessage(), e);
        }    
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Basic validation of response
    /////////////////////////////////////////////////////////////////////////
     */

    /**
     * Helper method used to verify that response is an OK response (2xx);
     * and if not, throw a {@link NingClientException} to indicate type of problem.
     */
    public void verifyResponse() throws NingClientException
    {
        int code = getStatusCode();
        if (code >= 200 && code < 300) {
            return;
        }

        // We might want to include headers too in future?
        /*
        com.ning.http.client.Headers h = response.rawResponse().getHeaders();
        System.err.println("DEBUG: headers == "+h);
        */
        String responseMessage = getTruncatedResponse();
        throw new NingHttpException(code, responseMessage);
    }
}
