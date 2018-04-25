package org.hejwo.testing.kafkamock.rules;

import java.util.Properties;

import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.hejwo.testing.kafkamock.general.properties.zookeeper.ZookeeperPropsBuilder;
import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ZookeeperMockRule extends ExternalResource {

    private final ZookeeperLocalThread zookeeperLocalThread;

    public static ZookeeperMockRule create() {
        log.debug("Created default Zookeeper rule");
        return new ZookeeperMockRule(ZookeeperPropsBuilder.buildDefault());
    }

    public static ZookeeperMockRule create(Properties zookeeperProperties) {
        log.debug("Created Zookeeper rule with properties: {}"+zookeeperProperties.toString());
        return new ZookeeperMockRule(ZookeeperPropsBuilder.buildWithCustomOnly(zookeeperProperties));
    }

    public static ZookeeperMockRule create(Integer port) {
        log.debug("Created Zookeeper rule at port : {}"+port.toString());
        return new ZookeeperMockRule(ZookeeperPropsBuilder.buildDefault(port));
    }

    private ZookeeperMockRule(ZookeeperPropsBuilder propertiesBuilder) {
        this.zookeeperLocalThread = new ZookeeperLocalThread(propertiesBuilder);
    }

    @Override
    protected void before() throws Throwable {
        if(!zookeeperLocalThread.isAlive()) {
            zookeeperLocalThread.start();
        }
    }

    @Override
    protected void after() {
        zookeeperLocalThread.shutdown();
    }

    public boolean isRunning() {
        return zookeeperLocalThread.isAlive();
    }

    public int getPort() {
        return zookeeperLocalThread.getPort();
    }

    public ZookeeperLocalThread getZookeeperLocalThread() {
        return zookeeperLocalThread;
    }
}
