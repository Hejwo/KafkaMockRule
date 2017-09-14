package org.hejwo.testing.kafkamock.general.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ZookeeperPropertyUtils {

    public static Path createTempZookeeperDataDir() {
        try {
            Path tempDirectory = Files.createTempDirectory("kafkamock-zookeeper");
            File myIdFile = new File(tempDirectory + "/myid");
            try (BufferedWriter myIdWritter = new BufferedWriter(new FileWriter(myIdFile))) {
                myIdWritter.write("1");
            }
            return tempDirectory;
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Properties createDefaultZookeeperProperties(Integer port) {
        Properties defaultProperties = createDefaultZookeeperProperties();
        defaultProperties.setProperty("clientPort", port.toString());
        return defaultProperties;
    }

    public static Properties createDefaultZookeeperProperties() {
        Path dataDirPath = createTempZookeeperDataDir();

        Properties zookeeperProps = new Properties();
        zookeeperProps.setProperty("dataDir", dataDirPath.toString());
        zookeeperProps.setProperty("tickTime", "2000");
        zookeeperProps.setProperty("clientPort", getRandomPort().toString());
        zookeeperProps.setProperty("initLimit", "5");
        zookeeperProps.setProperty("syncLimit", "2");
        return zookeeperProps;
    }

    private static Integer getRandomPort() {
        // According to Wikipedia, you should use ports 49152 to 65535 if you don't need a 'well known' port.
        return (int)(Math.random()*16383) + 49152;
    }


}
