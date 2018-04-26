package org.hejwo.testing.kafkamock.general;

import org.hejwo.testing.kafkamock.general.properties.kafka.KafkaPropsBuilder;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaLocalThread extends Thread {

    private final KafkaServerStartable kafka;
    private final KafkaPropsBuilder kafkaPropsBuilder;

    public KafkaLocalThread(KafkaPropsBuilder kafkaPropsBuilder) {
        this.setName("KafkaMockRule-KafkaThread");
        this.kafkaPropsBuilder = kafkaPropsBuilder;
        this.kafka = new KafkaServerStartable(new KafkaConfig(kafkaPropsBuilder.toProps()));
    }

    public KafkaPropsBuilder getKafkaPropsBuilder() {
        return kafkaPropsBuilder;
    }

    @Override
    public void run() {
        try {
            log.info("Starting kafka");
            kafka.startup();
            System.out.println("aaa");
        } catch(Throwable ex) {
            log.error("Error while starting !!!", ex);
        }
    }

    public void shutdown() {
        log.info("Shutting down Kafka");
        kafka.shutdown();
        // delete /tmp/   log.dirs=/tmp/kafka-logs
        // dataDir=/tmp/zookeeper
    }
}
