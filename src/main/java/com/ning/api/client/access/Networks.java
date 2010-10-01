package com.ning.api.client.access;

import com.ning.api.client.access.impl.DefaultLister;
import com.ning.api.client.access.impl.PagedListImpl;
import com.ning.api.client.action.PagedList;
import com.ning.api.client.item.*;

/**
 * Support for Network items (/Network end point). Simple read-only interface
 * used for listing information about networks created by "current user"
 * (User whose credentials are used by the client)
 */
public class Networks  extends Items<Network, NetworkField>
{
    public Networks(NingConnection connection)
    {
        super(connection, "Network", Network.class, NetworkField.class);
    }

    /*
    ///////////////////////////////////////////////////////////////////////
    // Public API: constructing request builders
    ///////////////////////////////////////////////////////////////////////
     */

    public Lister listerForAlpha(NetworkField firstField, NetworkField... otherFields) {
        return listerForAlpha(new Fields<NetworkField>(NetworkField.class, firstField, otherFields));
    }
    
    public Lister listerForAlpha(Fields<NetworkField> fields) {
        return new Lister(connection, defaultTimeoutForReadsMsecs, endpointForAlpha(), fields);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Request builder classes (Creator, Updater, Finder, UserLister, NetworkCounter)
    ///////////////////////////////////////////////////////////////////////
     */    

    public static class Lister extends DefaultLister<Network, NetworkField>
    {
        protected Lister(NingConnection connection, long timeoutMsecs, String endpoint,
                Fields<NetworkField> fields)
        {
            super(connection, timeoutMsecs, endpoint, fields, null, null, null);
        }

        @Override
        public PagedList<Network> list() {
            return new PagedListImpl<Network,NetworkField>(connection, timeoutMsecs, endpoint,
                    Network.class, fields, null, null, null);
        }
    }    
}
