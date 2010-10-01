package com.ning.api.client.exception;

import java.io.IOException;

import com.ning.api.client.sample.NingClientException;

/**
 * Exception used when exception was thrown due to a caught {@link IOException},
 * and the underlying cause is a data transfer problem. Such problems are usually
 * transient and often require different handling from other types such
 * as transformation exception.
 */
public class NingTransferException  extends NingClientException
{
    private static final long serialVersionUID = 1L;

    public NingTransferException(IOException e) {
        super(e);
    }

    public NingTransferException(String msg, IOException e) {
        super(msg, e);
    }
    
    /**
     * Most likely transient, so returns always true
     */
    @Override
    public boolean isRetryable() {
        return true;
    }
}
