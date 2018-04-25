package org.hejwo.testing.kafkamock.general.properties.zookeeper;

import java.nio.file.Path;

import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hejwo.testing.kafkamock.TestUtils.TEST_TIMEOUT;

public class ZookeeperLocalThreadTest {

    private static final int ZOOKEEPER_RUNTIME = 2000;

    @Test(timeout = TEST_TIMEOUT)
    public void shouldCreateInstanceBasedOnExternalProperties() throws InterruptedException {
        Path dataDirPath = ZookeeperPropertyUtils.createTempZookeeperDataDir();

        ZookeeperPropertiesBuilder zookeeperPropertiesBuilder = ZookeeperPropertiesBuilder.create()
            .dataDir(dataDirPath.toString())
            .tickTime(2000L)
            .clientPort(2181)
            .initLimit(5)
            .syncLimit(2);

        ZookeeperLocalThread zookeeperLocalThread = new ZookeeperLocalThread(zookeeperPropertiesBuilder);
        zookeeperLocalThread.start();

        assertThat(zookeeperLocalThread.isAlive()).isTrue();
        Thread.sleep(ZOOKEEPER_RUNTIME);
        zookeeperLocalThread.shutdown();
        assertThat(zookeeperLocalThread.isAlive()).isFalse();
    }

    @Test(timeout = TEST_TIMEOUT)
    public void shouldCreateInstanceBasedOnDefaultProperties() throws InterruptedException {
        ZookeeperLocalThread zookeeperLocalThread = new ZookeeperLocalThread(ZookeeperPropertiesBuilder.buildDefault());
        zookeeperLocalThread.start();

        assertThat(zookeeperLocalThread.isAlive()).isTrue();
        Thread.sleep(ZOOKEEPER_RUNTIME);
        zookeeperLocalThread.shutdown();
        assertThat(zookeeperLocalThread.isAlive()).isFalse();
    }

    @Test(timeout = TEST_TIMEOUT)
    public void shouldFailLoudOnRuntime() throws InterruptedException {
        ZookeeperPropertiesBuilder zookeeperPropertiesBuilder = ZookeeperPropertiesBuilder.buildDefault()
            .dataDir(" ");

        ZookeeperLocalThread zookeeperLocalThread = new ZookeeperLocalThread(zookeeperPropertiesBuilder);
        zookeeperLocalThread.start();

        assertThat(zookeeperLocalThread.isAlive()).isTrue();
        Thread.sleep(ZOOKEEPER_RUNTIME);
        zookeeperLocalThread.shutdown();
        assertThat(zookeeperLocalThread.isAlive()).isFalse();
    }
}