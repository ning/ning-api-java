package com.ning.api.client.json;

import java.io.IOException;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.CustomDeserializerFactory;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableDateTime;

/**
 * We need some minor customizations for object mapping, and easiest
 * way to do this is by sub-classing.
 * 
 * @author tatu
 */
public class ExtendedObjectMapper extends ObjectMapper
{
    public ExtendedObjectMapper()
    {
        super();
        configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // also: need to ensure that we can handle ReadableDateTime
        CustomDeserializerFactory f = new CustomDeserializerFactory();
        f.addSpecificMapping(ReadableDateTime.class, new ReadableDateTimeDeserializer());
        setDeserializerProvider(new StdDeserializerProvider(f));
    }

    private static class ReadableDateTimeDeserializer extends JsonDeserializer<ReadableDateTime>
    {
        @Override
        public DateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
        {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_INT) {
                return new DateTime(jp.getLongValue(), DateTimeZone.UTC);
            }
            if (t == JsonToken.VALUE_STRING) {
                return new DateTime(jp.getText().trim(), DateTimeZone.UTC);
            }
            throw new IOException("Can not deserialize ReadableDateTime out of "+t);
        }
    }
}
