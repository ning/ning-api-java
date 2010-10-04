package com.ning.api.client.sample;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.auth.AuthEntry;

public abstract class SampleBase
{
    protected final String host;
    protected final int httpPort;
    protected final int httpsPort;

    protected final String network;
    
    protected SampleBase(String host, int httpPort, int httpsPort,
            String network)
    {
        this.host = host;
        this.httpPort = httpPort;
        this.httpsPort = httpsPort;

        this.network = network;
    }

    protected abstract AuthEntry getConsumerKey();
    protected abstract AuthEntry getUserToken();

    public void action() throws Exception
    {
        NingClient client = new NingClient(network, getConsumerKey(), host, httpPort, httpsPort);
        NingConnection conn = client.connect(getUserToken());
        this.doAction(conn);
    }
    
    protected abstract void doAction(NingConnection conn) throws Exception;
}
