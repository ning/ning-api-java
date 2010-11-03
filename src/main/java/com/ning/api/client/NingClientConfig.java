package com.ning.api.client;

/**
 * Configuration object used for specifying configuration for operations.
 * It can be used at multiple levels to define per-client and per-connection
 * defaults, as well as per-request overrides.
 * All configuration values are defined using wrappers, to allow "undefined"
 * settings; these are usually resolved by using parent defaults.
 *<p>
 * Note that instances are immutable, and changing of configuration is always
 * made using "fluent" style factory methods. This has the benefit of all
 * configuration object instances being fully thread-safe without synchronization
 * (since instances can never be modified; new instances can be created with
 * differing settings)
 * 
 * @author tatu
 */
public class NingClientConfig
{
    /**
     * Default timeout used for read operations; 8 seconds.
     */
    protected final static int DEFAULT_READ_TIMEOUT_MSECS = 8000;

    /**
     * Default timeout used for write operations; 15 seconds.
     */
    protected final static int DEFAULT_WRITE_TIMEOUT_MSECS = 5000;

    /**
     * Default settings are such that https (ssl) is <b>not</b> used.
     */
    protected final static boolean DEFAULT_USE_HTTPS = Boolean.FALSE;
    
    /**
     * Instance that is fully populated with default settings; usually used as
     * the default value for client.
     */
    private final static NingClientConfig DEFAULT_SETTINGS = new NingClientConfig(
            DEFAULT_READ_TIMEOUT_MSECS,
            DEFAULT_WRITE_TIMEOUT_MSECS,
            DEFAULT_USE_HTTPS
            );
    
    /**
     * Timeout used for read operations, in milliseconds.
     */
    protected Integer readTimeoutMsecs;

    /**
     * Timeout used for write operations, in milliseconds.
     */
    protected Integer writeTimeoutMsecs;

    /**
     * Flag that indicates whether ssl (https) is to be used for URL connections
     * or not.
     * Default setting is 'false' to indicate that regular non-ssl HTTP connections
     * are used (along with HMAC-SHA1 signatures)
     */
    protected Boolean useSecureConnection;
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Construction
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Default constructor; creates an "empty" configuration (one with no values
     * defined), useful as baseline for instance that only overrides specific
     * values.
     */
    public NingClientConfig() {
        this(null, null, null);
    }

    public NingClientConfig(Integer readTimeoutMsecs, Integer writeTimeoutMsecs,
            Boolean useHttps)
    {
        this.readTimeoutMsecs = readTimeoutMsecs;
        this.writeTimeoutMsecs = writeTimeoutMsecs;
        this.useSecureConnection = useHttps;
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Factory methods
    ///////////////////////////////////////////////////////////////////////
     */

    public NingClientConfig withReadTimeoutMsecs(Integer value) {
        return new NingClientConfig(value, writeTimeoutMsecs, useSecureConnection);
    }

    public NingClientConfig withWriteTimeoutMsecs(Integer value) {
        return new NingClientConfig(readTimeoutMsecs, value, useSecureConnection);
    }

    /**
     * Method that will construct a configuration instance that indicates that
     * secure (https) connections are to be used when connecting.
     */
    public NingClientConfig withSecureConnection() {
        return new NingClientConfig(readTimeoutMsecs, writeTimeoutMsecs, Boolean.TRUE);
    }

    /**
     * Method that will construct a configuration instance that indicates that
     * secure (https) connections are <b>not</b> to be used when connecting;
     * regular http connections are used instead.
     */
    public NingClientConfig withNonSecureConnection() {
        return new NingClientConfig(readTimeoutMsecs, writeTimeoutMsecs, Boolean.TRUE);
    }

    /**
     * Method that will construct a configuration instance that indicates that
     * choice of secure or non-secure connection can be done using defaults (this
     * config instance does not define any preference)
     */
    public NingClientConfig withAnyConnection() {
        return new NingClientConfig(readTimeoutMsecs, writeTimeoutMsecs, null);
    }
    
    /**
     * Accessor for getting an instance with fully filled-out default settings.
     * This is useful as the ultimate default settings.
     */
    public static NingClientConfig defaults() { return DEFAULT_SETTINGS; }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Merging (for overrides, defaulting)
    ///////////////////////////////////////////////////////////////////////
     */

    /**
     * Factory method that will construct a new instance with overrides (non-null
     * values) from specific configuration, and otherwise defaulting to settings
     * this instance has.
     */
    public NingClientConfig overrideWith(NingClientConfig overrides)
    {
        if (overrides == null) {
            return this;
        }
        Integer readTimeout = choose(getReadTimeoutMsecs(), overrides.getReadTimeoutMsecs());
        Integer writeTimeout = choose(getWriteTimeoutMsecs(), overrides.getWriteTimeoutMsecs());
        Boolean useSecure = choose(getUseSecureConnection(), overrides.getUseSecureConnection());
        return new NingClientConfig(readTimeout, writeTimeout, useSecure);
    }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Accessors
    ///////////////////////////////////////////////////////////////////////
     */

    public Integer getReadTimeoutMsecs() { return readTimeoutMsecs; }
    public Integer getWriteTimeoutMsecs() { return writeTimeoutMsecs; }

    public Boolean getUseSecureConnection() { return useSecureConnection; }
    
    /*
    ///////////////////////////////////////////////////////////////////////
    // Helper methods
    ///////////////////////////////////////////////////////////////////////
     */

    protected <T> T choose(T baseline, T override)
    {
        if (override != null) {
            return override;
        }
        return baseline;
    }
}

