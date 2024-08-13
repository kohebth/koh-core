package koh.core.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServerConfiguration {
    final String host;
    final Integer port;
    final Integer securePort;
    final Integer maxBodySize;
    final Integer requestHeaderSize;
    final Integer responseHeaderSize;
    final Integer outputBufferSize;
    final Integer idleTimeout;
    final Integer maxThreadNum;
    final Integer minThreadNum;
    final String sslKeyStorePath;
    final String sslKeyStorePassword;
}
