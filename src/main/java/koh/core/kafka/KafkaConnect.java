package koh.core.kafka;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

@Getter
public class KafkaConnect {
    private final KafkaConsumer<String, String> consumer;
    private final KafkaProducer<String, String> producer;

    protected KafkaConnect() {
        KafkaConfig config = new KafkaConfig();
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getHost() + ":" + config.getPort());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroup());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, config.getValueDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, config.getValueDeserializer());

        consumer = new KafkaConsumer<>(props);
        producer = new KafkaProducer<>(props);
    }
}
