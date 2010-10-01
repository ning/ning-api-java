package com.ning.api.client.item;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;

public class BroadcastMessage extends ContentItemBase<BroadcastMessageField, BroadcastMessage>
{
    @JsonProperty protected String subject;
    @JsonProperty protected String body;
    @JsonProperty protected String messageId;

    // note: non-public since it's only to be called by deserializer:
    protected BroadcastMessage() { }
    
    public BroadcastMessage(String subject, String body)
    {
        this.subject = subject;
        this.body = body;
        
        // Since message id has to be unique, let's just create a random UUID
        messageId = UUID.randomUUID().toString();
    }

    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public String getMessageId() { return messageId; }
}
