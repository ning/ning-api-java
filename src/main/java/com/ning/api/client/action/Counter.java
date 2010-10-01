package com.ning.api.client.action;

import com.ning.api.client.sample.NingClientException;

/**
 * Simple common API for all types of counter objects; does not
 * include per-content-type configuration methods.
 */
public interface Counter
{
    /**
     * Method to call to actually execute call by sending request to
     * service.
     */
    public int count() throws NingClientException;
}
