package org.hejwo.testing.kafkamock.general;

import java.util.Properties;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaLocalThread extends Thread {

    private final KafkaServerStartable kafka;

    public KafkaLocalThread(Properties kafkaProperties) {
        this.setName("KafkaMockRule-KafkaThread");
        this.kafka = new KafkaServerStartable(new KafkaConfig(kafkaProperties));
    }

    @Override
    public void run() {
        try {
            log.info("Starting kafka");
            kafka.startup();
        } catch(Throwable ex) {
            log.error("Error while starting !!!", ex);
        }
    }

    public void shutdown() {
        log.info("Shutting down Kafka");
        kafka.shutdown();
    }
}
