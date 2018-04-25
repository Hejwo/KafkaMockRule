package org.hejwo.testing.kafkamock.general.properties.zookeeper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

class ZookeeperPropertyUtils {

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

    public static Integer getRandomPort() {
        // According to Wikipedia, you should use ports 49152 to 65535 if you don't need a 'well known' port.
        return (int)(Math.random()*16383) + 49152;
    }


}
