package com.ning.api.client.item;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.ReadableDateTime;

public class Friend extends ContentItemBase<FriendField, Friend> implements
        Cloneable {
    // mutable properties (for which setter exists), no need to annotate field
    protected String friend;

    // and things that app code can not change
    @JsonProperty
    protected ReadableDateTime updatedDate;
    @JsonProperty
    protected FriendState state;

    /**
     * Default constructor is non-public since it is only to be used by
     * serialization framework
     */
    protected Friend() {
        super();
    }

    public Friend(String friend) {
        super();
        this.friend = friend;
    }

    /**
     * Let's expose clone() for convenient immutable/fluent style pattern by
     * builders
     */
    public Friend clone() {
        try {
            return (Friend) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    // Read/write properties:
    public String getFriend() {
        return friend;
    }

    public void setFriend(String s) {
        friend = s;
    }

    // Read-only properties
    public FriendState getState() {
        return state;
    }

    public ReadableDateTime getUpdatedDate() {
        return updatedDate;
    }
}
