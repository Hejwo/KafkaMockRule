package org.hejwo.testing.kafkamock.general.properties.zookeeper;

import java.nio.file.Path;
import java.util.Properties;


public class ZookeeperPropertiesBuilder {

    Properties props = new Properties();

    public ZookeeperPropertiesBuilder tickTime(Long tickTimeMs) {
        props.setProperty("tickTime", tickTimeMs.toString());
        return this;
    }

    public ZookeeperPropertiesBuilder clientPort(Integer clientPort) {
        props.setProperty("clientPort", clientPort.toString());
        return this;
    }

    public ZookeeperPropertiesBuilder initLimit(Integer initLimit) {
        props.setProperty("initLimit", initLimit.toString());
        return this;
    }

    public ZookeeperPropertiesBuilder syncLimit(Integer syncLimit) {
        props.setProperty("syncLimit", syncLimit.toString());
        return this;
    }

    public ZookeeperPropertiesBuilder dataDir(String dataDir) {
        props.setProperty("dataDir", dataDir);
        return this;
    }

    public Properties toProps() {
        return props;
    }

    public static ZookeeperPropertiesBuilder create() {
        return new ZookeeperPropertiesBuilder();
    }

    public static ZookeeperPropertiesBuilder buildDefault(Integer port) {
        Path dataDirPath = ZookeeperPropertyUtils.createTempZookeeperDataDir();
        return new ZookeeperPropertiesBuilder().
            dataDir(dataDirPath.toString()).
            tickTime(2000L).
            clientPort(port).
            initLimit(5).
            syncLimit(2);
    }

    public static ZookeeperPropertiesBuilder buildDefaultOnRandomPort() {
        Integer randomPort = ZookeeperPropertyUtils.getRandomPort();
        return buildDefault(randomPort);
    }

    public static ZookeeperPropertiesBuilder buildDefault() {
        return buildDefault(2181);
    }

    public Properties buildWithCustom(Properties customProps) {
        Properties properties = new Properties();
        customProps.putAll(props);
        customProps.putAll(customProps);
        return properties;
    }

    public static ZookeeperPropertiesBuilder buildWithCustomOnly(Properties customProps) {
        ZookeeperPropertiesBuilder builder = new ZookeeperPropertiesBuilder();
        builder.props = customProps;
        return builder;
    }

}
