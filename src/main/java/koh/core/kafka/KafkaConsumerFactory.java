package koh.core.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class KafkaConsumerFactory {
    Map<String, Function<ConsumerRecord<String, String>, Void>> topicConsumers;
    final KafkaConnect kafka;
    static final Function<ConsumerRecord<String, String>, Void> CONSUMER_DO_NOTHING = m -> {
    };

    KafkaConsumerFactory(KafkaConnect kafka) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(this::poll);
    }

    public void createConsumerHandler(String topic, Function<ConsumerRecord<String, String>, Void> consumerHandler) {
        topicConsumers.put(topic, consumerHandler);
    }

    void poll() {
        Consumer<String, String> consumer = this.kafka.getConsumer();
        consumer.
        Duration pollDuration = Duration.of(2500, ChronoUnit.MILLIS);
        try {
            while (true) {
                ConsumerRecords<String, String> messages = consumer.poll(pollDuration);
                messages.forEach(this::consume);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    void consume(ConsumerRecord<String, String> message) {
        this.topicConsumers.getOrDefault(message.topic(), CONSUMER_DO_NOTHING).apply(message);
    }
}
