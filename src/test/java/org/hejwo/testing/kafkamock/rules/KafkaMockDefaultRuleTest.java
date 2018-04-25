package org.hejwo.testing.kafkamock.rules;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

//@Ignore
public class KafkaMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 6000;

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    //    @Test(timeout = TEST_TIMEOUT)
    @Test
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        Properties properties = createKafkaProducerProperties();

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> whatsup = new ProducerRecord<>("test1", "testRecord !!!");

        producer.send(whatsup, createValidatingCallback("test1"));

        ConsumerRecords<String, String> records = kafkaMockRule.lookup().poolRecords();


    }

    private Properties createKafkaProducerProperties() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("topic.name", "test1");
        properties.setProperty("acks", "1"); // Matching default
        properties.setProperty("retries", "0");
        properties.setProperty("timeout.ms", "1500");
        return properties;
    }

    private Callback createValidatingCallback(final String topicName) {
        return new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                assertThat(metadata.topic()).isEqualToIgnoringCase(topicName);
                assertThat(exception).isNull();
            }
        };
    }
}