package com.ning.api.client.item;

import java.net.URI;

import org.codehaus.jackson.annotate.JsonProperty;

import org.joda.time.ReadableDateTime;

/**
 * Complete user item.
 *<p>
 * Note on method visibility: setters that are only to be used by serialization
 * system are left protected. Public setters are added for fields that can be
 * modified through Rest API.
 */
public class User
    extends ContentItemBase<UserField, User>
    implements Cloneable
{
    // mutable properties (for which setter exists), no need to annotate field
    protected Boolean approved;
    protected String statusMessage;
    
    // and things that app code can not change
    @JsonProperty protected ReadableDateTime updatedDate;
    @JsonProperty protected String email;
    @JsonProperty protected URI url;
    @JsonProperty protected String fullName;
    @JsonProperty protected String gender;
    @JsonProperty("birthdate") protected ReadableDateTime birthDate;
    @JsonProperty protected URI iconUrl;
    @JsonProperty protected Visibility visibility;
    @JsonProperty protected Integer commentCount;
    @JsonProperty protected String state;
    @JsonProperty protected Boolean isAdmin;
    @JsonProperty protected Boolean isBlocked;
    @JsonProperty protected Boolean isMember;
    @JsonProperty protected Boolean isOwner;
    @JsonProperty protected String location;
    
    public User() { this((String) null); }
    public User(String id) { this(new Key<User>(id)); }
    public User(Key<User> id) { this.id = id; }

    /**
     * Let's expose clone() for convenient immutable/fluent style pattern by
     * builders
     */
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }
    
    // // // Fields that are mutable via Rest API?

    public Boolean isApproved() { return approved; }
    public void setApproved(Boolean b) { approved = b; }
    
    public String getStatusMessage() { return statusMessage; }
    public void setStatusMessage(String msg) { statusMessage = msg; }

    // // // And then immutable fields
    
    public ReadableDateTime getUpdatedDate() { return updatedDate; }
    public String getEmail() { return email; }
    public URI getUrl() { return url; }
    public String getFullName() { return fullName; }
    public String getGender() { return gender; }
    @JsonProperty("birthdate")
    public ReadableDateTime getBirthDate() { return birthDate; }
    public URI getIconUrl() { return iconUrl; }
    public Visibility getVisibility() { return visibility; }
    public Integer getCommentCount() { return commentCount; }
    public String getState() { return state; }

    @JsonProperty("isAdmin")
    public Boolean isAdmin() { return isAdmin; }
    @JsonProperty("isBlocked")
    public Boolean isBlocked() { return isBlocked; }
    @JsonProperty("isMember")
    public Boolean isMember() { return isMember; }
    @JsonProperty("isOwner")
    public Boolean isOwner() { return isOwner; }
    
    public String getLocation() { return location; }
}
