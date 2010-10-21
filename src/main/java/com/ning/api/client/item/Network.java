package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;

public class Network extends ContentItemBase<NetworkField, Network>
{
    // Base class includes standard ones: id, author, createdDate
    
    @JsonProperty protected String subdomain;
    @JsonProperty protected String name;
    @JsonProperty protected XapiStatus xapiStatus;

    @JsonProperty protected URI iconUrl;
    @JsonProperty protected URI defaultUserIconUrl;

    @JsonProperty protected Boolean blogPostModeration;
    @JsonProperty protected Boolean eventModeration;
    @JsonProperty protected Boolean groupModeration;
    @JsonProperty protected Boolean photoModeration;
    @JsonProperty protected Boolean userModeration;
    @JsonProperty protected Boolean videoModeration;

    public Network() { }

    public String getSubdomain() { return subdomain; }
    public String getName() { return name; }
    public XapiStatus getXapiStatus() { return xapiStatus; }
    
    public URI getIconUrl() { return iconUrl; }
    public URI getDefaultUserIconUrl() { return defaultUserIconUrl; }

    public Boolean getBlogPostModeration() { return blogPostModeration; }
    public Boolean getEventModeration() { return eventModeration; }
    public Boolean getGroupModeration() { return groupModeration; }
    public Boolean getPhotoModeration() { return photoModeration; }
    public Boolean getUserModeration() { return userModeration; }
    public Boolean getVideoModeration() { return videoModeration; }
}
