package koh.core.config;

import koh.core.base.SimpleConfiguration;

public enum ServerConfigurationDefault implements SimpleConfiguration {
    HOST("0.0.0.0"),
    PORT("80"),
    SECURE_PORT("443"),
    MAX_BODY_SIZE(String.valueOf(24 * 1024 * 1024)),
    REQUEST_HEADER_SIZE(String.valueOf(8 * 1024)),
    RESPONSE_HEADER_SIZE(String.valueOf(8 * 1024)),
    OUTPUT_BUFFER_SIZE(String.valueOf(32 * 1024)),
    IDLE_TIMEOUT("30000"),
    MAX_THREAD_NUM("4"),
    MIN_THREAD_NUM("0"),
    SSL_KEY_STORE_PATH("/etc/ssl/key"),
    SSL_KEY_STORE_PASSWORD("/etc/ssl/key/pw");

    private final String defaultValue;

    ServerConfigurationDefault(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String fromSystemEnv() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String fromSystemProperties() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String value() {
        return this.defaultValue;
    }
}
