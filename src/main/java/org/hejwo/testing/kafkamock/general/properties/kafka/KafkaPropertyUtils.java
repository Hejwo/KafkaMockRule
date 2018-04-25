package org.hejwo.testing.kafkamock.general.properties.kafka;

import java.util.Properties;

public class KafkaPropertyUtils {

    public static Properties createDefaultKafkaProperties() {
        Properties properties = new Properties();
        properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("zookeeper.connection.timeout.ms", "1000");
        return properties;
    }
}
