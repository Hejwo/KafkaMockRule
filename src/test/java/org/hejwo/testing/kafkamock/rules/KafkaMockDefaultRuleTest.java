package org.hejwo.testing.kafkamock.rules;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class KafkaMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 32000;

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    @Test
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        System.out.println("Zookeeper running ? "+kafkaMockRule.isZookeeperRunning());
        System.out.println("Kafka running ? "+kafkaMockRule.isKafkaRunning());

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("topic.name", "test1");
        properties.setProperty("acks", "1"); // Matching default
        properties.setProperty("retries", "0");
        properties.setProperty("timeout.ms", "1500");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> whatsup = new ProducerRecord<>("test1", "testRecord !!!");

        producer.send(whatsup, getCallback());

    }

    private Callback getCallback() {
        return new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                System.out.println("Metadata: "+metadata);
                System.out.println("Excepction: "+exception);
            }
        };
    }
}