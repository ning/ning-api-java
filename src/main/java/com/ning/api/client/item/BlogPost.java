package com.ning.api.client.item;

import java.net.URI;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.ReadableDateTime;

public class BlogPost
    extends ContentItemBase<BlogPostField, BlogPost>
    implements Cloneable
{
    // mutable properties (for which setter exists), no need to annotate field
    protected String title;
    protected String description;
    protected Visibility visibility;
    protected ReadableDateTime publishTime;
    protected PublishStatus publishStatus;

    protected Boolean approved;

    // and things that app code can not change

    @JsonProperty protected ReadableDateTime updatedDate;
    /**
     * Link to browser-viewable representation of the item
     */
    @JsonProperty protected URI url;
    @JsonProperty protected Integer commentCount;
    @JsonProperty protected List<String> topTags;
    @JsonProperty("birthdate") protected ReadableDateTime birthDate;
    @JsonProperty protected String email;

    // TODO: sub-properties; author.xxx

    /**
     * Default constructor is non-public since it is only to be used by
     * serialization framework
     */
    protected BlogPost() { super(); }

    public BlogPost(Key<BlogPost> id) {
        this.id = id;
    }

    public BlogPost(String title, String description)
    {
        super();
        this.title = title;
        this.description = description;
    }

    /**
     * Let's expose clone() for convenient immutable/fluent style pattern by
     * builders
     */
    public BlogPost clone() {
        try {
            return (BlogPost) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    // Read/write properties:

    public String getTitle() { return title; }
    public void setTitle(String s) { title = s; }

    public String getDescription() { return description; }
    public void setDescription(String s) { description = s; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility v) { visibility = v; }

    public Boolean isApproved() { return approved; }
    public void setApproved(Boolean b) { approved = b; }

    public ReadableDateTime getPublishTime() { return publishTime; }
    public void setPublishTime(ReadableDateTime d) { publishTime = d; }

    public PublishStatus getPublishStatus() { return publishStatus; }
    public void setPublishStatus(PublishStatus s) { publishStatus = s; }

    // Read-only properties

    public ReadableDateTime getUpdatedDate() { return updatedDate; }
    public ReadableDateTime getBirthDate() { return birthDate; }
    public Integer getCommentCount() { return commentCount; }
    public String getEmail() { return email; }
    public List<String> getTopTags() { return topTags; }
    public URI getUrl() { return url; }

}
