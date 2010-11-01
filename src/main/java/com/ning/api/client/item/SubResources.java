package com.ning.api.client.item;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Container used for containing and accessing sub-resources that are associated with
 * and shared by actual content items.
 */
public class SubResources
{
    /**
     * Object mapper we use if sub-resource binding is needed
     */
    protected final ObjectMapper objectMapper;
    
    protected final JsonNode resources;

    /**
     * If and when a sub-resource gets bound, we will retain result to avoid
     * excessive deserialization costs
     */
    private transient HashMap<String, Object> boundResources;

    public SubResources(ObjectMapper m, JsonNode r)
    {
        objectMapper = m;
        resources = r;
    }

    public <RT> RT findOrLoadResource(Class<RT> type, String id)
    {
        return findOrLoadResource(type, id, null);
    }
    
    public <RT> RT findOrLoadResource(Class<RT> type, String id, RT instance)
    {
        RT value = findResource(type, id);
        if (value == null) {
            value = loadResource(type, id, instance);
        }
        return value;
    }
    
    /**
     * Method for finding already resolved sub-resource instance by id
     */
    protected <RT> RT findResource(Class<RT> type, String id)
    {
        if (boundResources != null) {
            Object ob = boundResources.get(id);
            if (ob != null) { // sanity check
                if (!(ob instanceof Author)) {
                    throw new IllegalArgumentException("Type problem: sub-resource '"+id+"' not of type "
                            +type.getSimpleName()+", but of type "+ob.getClass().getSimpleName());
                }
                @SuppressWarnings("unchecked")
                RT result = (RT) ob;
                return result;
            }
        }
        return null;
    }

    /**
     * Method for loading specified resources
     */
    protected <RT> RT loadResource(Class<RT> type, String id, RT instance)
    {
        if (resources == null || id == null) {
            return null;
        }
        JsonNode entry = resources.get(id);
        if (entry == null) {
            return null;
        }
        // Ok: should be an object... if not, indicate error
        if (!entry.isObject()) {
            throw new IllegalArgumentException("Corrupt or malformed data: "+type.getSimpleName()+" data for '"+id
                    +"' not a JSON object but "+entry.asToken());
        }
        // Then try binding
        try {
            if (instance == null) {
                instance = objectMapper.treeToValue(entry, type);
            } else {
                objectMapper.updatingReader(instance).readValue(entry.traverse());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Corrupt or malformed data: author data for '"+id
                    +"' could not be bound, problem: "+e.getMessage());
        }
        if (boundResources == null) {
            boundResources = new HashMap<String,Object>();
        }
        boundResources.put(id, instance);
        return instance;
    }
}
