package com.ning.api.client.exception;

import com.ning.api.client.NingClientException;

public class NingInterruptionException extends NingClientException
{
    private static final long serialVersionUID = 1L;

    public NingInterruptionException(InterruptedException e) {
        super(e);
    }

    /**
     * If a request is interrupted, chances are it should NOT be retries
     * as-is (or maybe at all); so always returns false.
     */
    @Override
    public boolean isRetryable() {
        return false;
    }

}
