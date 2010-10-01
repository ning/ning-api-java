package com.ning.api.client.item;

/**
 * Enumeration of values for Network.xapiStatus property; status is based
 * on whether calling application (as determined by consumer key) has XApi
 * functionality available for further operation.
 * Note that values other that "enabled" can only be returned when network
 * information is accessed through networks that have external API enabled.
 */
public enum XapiStatus {
    /**
     * Current application has external api enabled.
     */
    enabled,

    /**
     * Current application does not have API enabled, but it could be enabled.
     */
    not_enabled, 

    /**
     * Current application does not have API enabled, and network itself does
     * not support it with current subscription (needs upgrade)
     */
    needs_upgrade,

    /**
     * Network is in failed state, and no information could be accessed.
     */
    invalid
    ;
}
