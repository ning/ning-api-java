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
     * Instance that is fully populated with default settings; usually used as
     * the default value for client.
     */
    private final static NingClientConfig DEFAULT_SETTINGS = new NingClientConfig(
            DEFAULT_READ_TIMEOUT_MSECS,
            DEFAULT_WRITE_TIMEOUT_MSECS
            );
    
    /**
     * Timeout used for read operations, in milliseconds.
     */
    protected Integer readTimeoutMsecs;

    /**
     * Timeout used for write operations, in milliseconds.
     */
    protected Integer writeTimeoutMsecs;

    /*
    /////////////////////////////////////////////////////////////////////////
    // Construction
    /////////////////////////////////////////////////////////////////////////
     */
    
    public NingClientConfig() {
        this(DEFAULT_READ_TIMEOUT_MSECS, DEFAULT_WRITE_TIMEOUT_MSECS);
    }

    public NingClientConfig(Integer readTimeoutMsecs, Integer writeTimeoutMsecs)
    {
        this.readTimeoutMsecs = readTimeoutMsecs;
        this.writeTimeoutMsecs = writeTimeoutMsecs;
    }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Factory methods
    /////////////////////////////////////////////////////////////////////////
     */

    public NingClientConfig withReadTimeoutMsecs(Integer value) {
        return new NingClientConfig(value, writeTimeoutMsecs);
    }

    public NingClientConfig withWriteTimeoutMsecs(Integer value) {
        return new NingClientConfig(readTimeoutMsecs, value);
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Merging (for overrides, defaulting)
    /////////////////////////////////////////////////////////////////////////
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
        return new NingClientConfig(readTimeout, writeTimeout);
    }
    
    /*
    /////////////////////////////////////////////////////////////////////////
    // Accessors
    /////////////////////////////////////////////////////////////////////////
     */

    /**
     * Accessor for getting an instance with fully filled-out default settings.
     * This is useful as the ultimate default settings.
     */
    public static NingClientConfig defaults() { return DEFAULT_SETTINGS; }
    
    public int getReadTimeoutMsecs() { return readTimeoutMsecs; }
    public int getWriteTimeoutMsecs() { return writeTimeoutMsecs; }

    /*
    /////////////////////////////////////////////////////////////////////////
    // Helper methods
    /////////////////////////////////////////////////////////////////////////
     */

    protected <T> T choose(T baseline, T override)
    {
        if (override != null) {
            return override;
        }
        return baseline;
    }
}

