package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Value class used to represent "image sub-resource" -- piece of data associated with
 * primary items that can be requested along with main data for some content types.
 */
public class Image
{
    @JsonProperty protected URI url;
    @JsonProperty protected Double height;
    @JsonProperty protected Double width;

    public Image() {
        this(null, null, null);
    }

    public Image(URI url, Double width, Double height)
    {
        this.url = url;
    }
    
    public URI getUrl() { return url; }
    public Double getWidth() { return width; }
    public Double getHeight() { return height; }
}
