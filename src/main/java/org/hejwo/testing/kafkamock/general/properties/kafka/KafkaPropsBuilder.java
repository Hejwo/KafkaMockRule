package org.hejwo.testing.kafkamock.general.properties.kafka;

import java.util.Properties;

public class KafkaPropsBuilder {

    private Properties properties = new Properties();

    public static KafkaPropsBuilder create() {
        return new KafkaPropsBuilder();
    }

    public KafkaPropsBuilder zookeeperConnect(String zookeeperConnect) {
        properties.setProperty("zookeeper.connect", zookeeperConnect);
        return this;
    }

    public KafkaPropsBuilder zookeeperConnectionTimeoutMs(Long zookeeperConnectionTimeoutMs) {
        properties.setProperty("zookeeper.connection.timeout.ms", zookeeperConnectionTimeoutMs.toString());
        return this;
    }

    public static KafkaPropsBuilder buildDefault() {
        return KafkaPropsBuilder.create()
            .zookeeperConnect("localhost:2181")
            .zookeeperConnectionTimeoutMs(1000L);
    }

    public Properties toProps() {
        return properties;
    }

}
