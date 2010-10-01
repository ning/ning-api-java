package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Video extends ContentItemBase<VideoField, Video>
{
    // Read/write properties:

    protected String description;
    protected String title;

    // Read-only properties:

    /**
     * Link to browser-viewable representation of the item
     */
    @JsonProperty protected URI url;
    @JsonProperty protected List<String> tags;
    
    public Video() { this(null); }
    public Video(Key<Video> id) {
        this.id = id;
    }

    // Read/write properties:
    
    public String getDescription() { return description; }
    public void setDescription(String s) { description = s; }

    public String getTitle() { return title; }
    public void setTitle(String s) { title = s; }

    // Other properties

    public URI getUrl() { return url; }
    public List<String> getTags() { return tags; }
}
