package org.hejwo.testing.kafkamock.general.utils;

import java.util.Properties;

public class KafkaPropertyUtils {

    public static Properties createDefaultKafkaProperties() {
        Properties properties = new Properties();
        properties.setProperty("zookeeper.connect", "");
        return properties;
    }
}
