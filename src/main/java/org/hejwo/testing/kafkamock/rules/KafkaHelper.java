package org.hejwo.testing.kafkamock.rules;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.hejwo.testing.kafkamock.general.properties.kafka.KafkaPropsBuilder;

class KafkaHelper {

    private final KafkaPropsBuilder kafkaPropsBuilder;
    private final String kafkaAddress;

    public KafkaHelper(KafkaPropsBuilder kafkaPropsBuilder) {
        this.kafkaPropsBuilder = kafkaPropsBuilder;
        this.kafkaAddress = "localhost:9092";
    }

    public <T, U, K extends Serializer<T>, V extends Serializer<U>> Producer<T, U> createProducer(
        Class<K> keySerializer,
        Class<V> valueSerializer,
        String topicName
    ) {
        Properties producerProps = createKafkaProducerProperties(kafkaAddress, keySerializer, valueSerializer, topicName);
        KafkaProducer<T, U> producer = new KafkaProducer<>(producerProps);
        return producer;
    }

    private static Properties createKafkaProducerProperties(String kafkaAddress,
                                                            Class<? extends Serializer<?>> keySerializer,
                                                            Class<? extends Serializer<?>> valueSerializer,
                                                            String topicName) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("key.serializer", keySerializer.getName());
        properties.setProperty("value.serializer", valueSerializer.getName());
        properties.setProperty("topic.name", topicName);
        properties.setProperty("acks", "1"); // Matching default
        properties.setProperty("retries", "0");
        properties.setProperty("timeout.ms", "1500");
        return properties;
    }

    public <T, U, K extends Deserializer<T>, V extends Deserializer<U>> Consumer<T, U> createConsumer(
        Class<K> keyDeserializer,
        Class<V> valueDeserializer,
        String topicName,
        String groupId
    ) {
        Properties consumerProps = createKafkaConsumerProperties(kafkaAddress, keyDeserializer, valueDeserializer, topicName, groupId);
        Consumer<T, U> consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Arrays.asList(topicName));
        return consumer;
    }

    private static Properties createKafkaConsumerProperties(String kafkaAddress,
                                                            Class<? extends Deserializer<?>> keyDeserializer,
                                                            Class<? extends Deserializer<?>> valueDeserializer,
                                                            String topicName,
                                                            String groupId) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", kafkaAddress);
        properties.setProperty("key.deserializer", keyDeserializer.getName());
        properties.setProperty("value.deserializer", valueDeserializer.getName());
        properties.setProperty("topic.name", topicName);
        properties.setProperty("acks", "1"); // Matching default
        properties.setProperty("retries", "0");
        properties.setProperty("timeout.ms", "1500");
        properties.setProperty("group.id", groupId);
        return properties;
    }

}
