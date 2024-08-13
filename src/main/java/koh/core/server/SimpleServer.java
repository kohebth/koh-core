package koh.core.server;

import koh.core.base.AbstractService;
import koh.core.base.HttpMethod;
import koh.core.base.SimpleService;
import koh.core.config.RouteConfiguration;
import koh.core.config.ServerConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static koh.core.config.ServerConfigurationDefault.*;

@Slf4j
public abstract class SimpleServer extends ServerHolder {

    ServerConfiguration.ServerConfigurationBuilder serverConfigurationBuilder;
    RouteConfiguration.RouteConfigurationBuilder routeConfigurationBuilder;

    protected SimpleServer() {
        serverConfigurationBuilder = ServerConfiguration.builder();
        host(HOST.value());
        port(Integer.parseInt(PORT.value()));
        securePort(Integer.parseInt(SECURE_PORT.value()));
        maxBodySize(Integer.parseInt(MAX_BODY_SIZE.value()));
        requestHeaderSize(Integer.parseInt(REQUEST_HEADER_SIZE.value()));
        responseHeaderSize(Integer.parseInt(RESPONSE_HEADER_SIZE.value()));
        outputBufferSize(Integer.parseInt(OUTPUT_BUFFER_SIZE.value()));
        idleTimeout(Integer.parseInt(IDLE_TIMEOUT.value()));
        maxThreadNum(Integer.parseInt(MAX_THREAD_NUM.value()));
        minThreadNum(Integer.parseInt(MIN_THREAD_NUM.value()));
        sslKeyStorePath(SSL_KEY_STORE_PATH.value());
        sslKeyStorePassword(SSL_KEY_STORE_PASSWORD.value());

        routeConfigurationBuilder = RouteConfiguration.builder();

        config();
    }

    @Override
    ServerConfiguration initializeServerConfiguration() {
        return serverConfigurationBuilder.build();
    }

    @Override
    RouteConfiguration initializeRoute() {
        return routeConfigurationBuilder.build();
    }

    protected void host(String value) {
        serverConfigurationBuilder.host(value);
    }

    protected void port(Integer value) {
        serverConfigurationBuilder.port(value);
    }

    protected void securePort(Integer value) {
        serverConfigurationBuilder.securePort(value);
    }

    protected void maxBodySize(Integer value) {
        serverConfigurationBuilder.maxBodySize(value);
    }

    protected void requestHeaderSize(Integer value) {
        serverConfigurationBuilder.requestHeaderSize(value);
    }

    protected void responseHeaderSize(Integer value) {
        serverConfigurationBuilder.responseHeaderSize(value);
    }

    protected void outputBufferSize(Integer value) {
        serverConfigurationBuilder.outputBufferSize(value);
    }

    protected void idleTimeout(Integer value) {
        serverConfigurationBuilder.idleTimeout(value);
    }

    protected void maxThreadNum(Integer value) {
        serverConfigurationBuilder.maxThreadNum(value);
    }

    protected void minThreadNum(Integer value) {
        serverConfigurationBuilder.minThreadNum(value);
    }

    protected void sslKeyStorePath(String value) {
        serverConfigurationBuilder.sslKeyStorePath(value);
    }

    protected void sslKeyStorePassword(String value) {
        serverConfigurationBuilder.sslKeyStorePassword(value);
    }

    protected void route(HttpMethod method, String path, Class<? extends AbstractService> serviceClass) {
        routeConfigurationBuilder.route(new RouteConfiguration.Route(method, path, serviceClass));
    }

    /**
     * Implementation: call {@link #route(HttpMethod, String, Class)} to add a route
     */
    protected abstract void config();
}
