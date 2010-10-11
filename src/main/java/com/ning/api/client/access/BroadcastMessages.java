package com.ning.api.client.access;

import com.ning.api.client.NingClientConfig;
import com.ning.api.client.access.impl.DefaultCreator;
import com.ning.api.client.action.Creator;
import com.ning.api.client.http.NingHttpPost;
import com.ning.api.client.item.*;

public class BroadcastMessages extends Items<BroadcastMessage, BroadcastMessageField>
{
    public BroadcastMessages(NingConnection connection, NingClientConfig config)
    {
        super(connection, config, "BroadcastMessage", BroadcastMessage.class, BroadcastMessageField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Creator<BroadcastMessage> creator(BroadcastMessage message) {
        return new BroadcastMessageCreator(connection, config, endpointForPOST(), message);
    }

    public static class BroadcastMessageCreator extends DefaultCreator<BroadcastMessage>
    {
        protected final String subject;
        protected final String body;
        protected final String messageId;
        
        public BroadcastMessageCreator(NingConnection connection, NingClientConfig config, String endpoint,
                BroadcastMessage message)
        {
            super(connection, config, endpoint);
            subject = message.getSubject();
            body = message.getBody();
            messageId = message.getMessageId();
        }

        @Override
        protected NingHttpPost addCreateParameters(NingHttpPost create)
        {
            return create.addFormParameter("subject", subject)
                .addFormParameter("body", body)
                .addFormParameter("messageId", messageId)
                ;
        }
    }
    
}
