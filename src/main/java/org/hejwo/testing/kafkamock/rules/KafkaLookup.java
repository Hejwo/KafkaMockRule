package org.hejwo.testing.kafkamock.rules;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

class KafkaLookup {

    private Properties createKafkaConsumerProperties() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("topic.name", "test1");
        properties.setProperty("acks", "1"); // Matching default
        properties.setProperty("retries", "0");
        properties.setProperty("timeout.ms", "1500");
        properties.setProperty("group.id", "aaa");
        return properties;
    }

    public ConsumerRecords<String, String> poolRecords(long poolTimeMs) {
        Consumer<String, String> consumer = new KafkaConsumer<String, String>(createKafkaConsumerProperties());
        consumer.subscribe(Arrays.asList("test1"));
        return consumer.poll(poolTimeMs);
    }

    public ConsumerRecords<String, String> poolRecords() {
        return poolRecords(800);
    }


}
