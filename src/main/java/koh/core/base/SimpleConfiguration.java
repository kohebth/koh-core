package koh.core.base;

public interface SimpleConfiguration {
    String fromSystemEnv();

    String fromSystemProperties();

    <T> T value();
}
