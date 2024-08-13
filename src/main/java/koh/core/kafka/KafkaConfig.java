package koh.core.kafka;

import lombok.Data;

@Data
public class KafkaConfig {
    private final String host;
    private final String port;
    private final String group;
    private final Class<?> keyDeserializer;
    private final Class<?> valueDeserializer;

    public KafkaConfig() {
        host = System.getenv("KAFKA_HOST");
        port = System.getenv("KAFKA_HOST");
        group = System.getenv("KAFKA_GROUP");
        keyDeserializer = org.apache.kafka.common.serialization.StringDeserializer.class;
        valueDeserializer = org.apache.kafka.common.serialization.StringDeserializer.class;
    }
}
