package org.hejwo.testing.kafkamock;

import java.nio.file.Path;
import java.util.Properties;

import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.junit.Test;

import static org.hejwo.testing.kafkamock.general.utils.ZookeeperPropertyUtils.createDefaultZookeeperProperties;
import static org.hejwo.testing.kafkamock.general.utils.ZookeeperPropertyUtils.createTempZookeeperDataDir;

public class ZookeeperLocalThreadTest {

    private static final int ZOOKEEPER_RUNTIME = 2000;

    @Test
    public void shouldCreateInstanceBasedOnExternalProperties() throws InterruptedException {
        Path dataDirPath = createTempZookeeperDataDir();

        Properties zookeeperProps = new Properties();
        zookeeperProps.setProperty("dataDir", dataDirPath.toString());
        zookeeperProps.setProperty("tickTime", "2000");
        zookeeperProps.setProperty("clientPort", "2181");
        zookeeperProps.setProperty("initLimit", "5");
        zookeeperProps.setProperty("syncLimit", "2");

        ZookeeperLocalThread zookeeperLocalThread = new ZookeeperLocalThread(zookeeperProps);
        zookeeperLocalThread.start();
        Thread.sleep(ZOOKEEPER_RUNTIME);
    }

    @Test
    public void shouldCreateInstanceBasedOnDefaultProperties() throws InterruptedException {
        ZookeeperLocalThread zookeeperLocalThread = new ZookeeperLocalThread(createDefaultZookeeperProperties());
        zookeeperLocalThread.start();
        Thread.sleep(ZOOKEEPER_RUNTIME);
    }
}