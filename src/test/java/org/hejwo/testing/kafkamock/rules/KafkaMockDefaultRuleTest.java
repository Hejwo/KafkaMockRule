package org.hejwo.testing.kafkamock.rules;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hejwo.testing.kafkamock.TestUtils.TEST_TIMEOUT;

public class KafkaMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 6000;

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    @Test(timeout = TEST_TIMEOUT)
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        Producer<String, String> producer = kafkaMockRule.helper().createProducer(StringSerializer.class, StringSerializer.class, "test1");
        ProducerRecord<String, String> testRecord = new ProducerRecord<>("test1", "testRecord !!!");
        producer.send(testRecord, createValidatingCallback("test1"));

        Consumer<String, String> consumer = kafkaMockRule.helper()
            .createConsumer(StringDeserializer.class, StringDeserializer.class, "test1", "group1");

        ConsumerRecords<String, String> poll = consumer.poll(1000);

        System.out.println(poll);
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