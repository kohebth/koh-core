package koh.core.server;

import jakarta.servlet.http.HttpServlet;
import koh.core.config.RouteConfiguration;
import koh.core.config.ServerConfiguration;
import koh.core.helper.JsonTools;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
abstract class ServerHolder {
    private ServerConfiguration serverConfiguration;
    private Server serverInstant;

    public void start() {
        serverConfiguration = initializeServerConfiguration();
        serverInstant = new Server(initializeThreadPool());
        serverInstant.setHandler(initializeHandlers());
        serverInstant.setConnectors(initializeConnectors());

        try {
            log.info(JsonTools.toJson(serverConfiguration));
            serverInstant.start();
            serverInstant.join();
        } catch (InterruptedException e) {
            log.error("HTTP Server interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Failed to start HTTP Server!", e);
        } finally {
            serverInstant.destroy();
        }
    }

    Connector[] initializeConnectors() {
        try (ServerConnector serverConnector = new ServerConnector(this.serverInstant)) {
            serverConnector.setPort(serverConfiguration.getPort());
            serverConnector.setIdleTimeout(serverConfiguration.getIdleTimeout());
            serverConnector.setHost(serverConfiguration.getHost());
            log.info("Server connectors have been initialized");
            return new Connector[]{serverConnector};
        }
    }

    Handler initializeHandlers() {
        ServletContextHandler singleHandler = new ServletContextHandler();
        initializeRoute()
                .getRoutes()
                .stream()
                .collect(Collectors.toMap(RouteConfiguration.Route::getPath, Stream::of, Stream::concat))
                .forEach((path, stream) -> {
                    RouteHolder routeHolder = new RouteHolder();
                    stream.forEach(route -> routeHolder.addService(route.getMethod(), route.getServiceClass()));
                    singleHandler.addServlet(new ServletHolder(routeHolder), path);
                });
        log.info("Server core handlers have been initialized");
        return singleHandler;
    }

    ThreadPool initializeThreadPool() {
        return new QueuedThreadPool(serverConfiguration.getMaxThreadNum(),
                serverConfiguration.getMinThreadNum(),
                30 * 60 * 1000
        );
    }

    abstract ServerConfiguration initializeServerConfiguration();

    abstract RouteConfiguration initializeRoute();
}
