package org.hejwo.testing.kafkamock.general.properties.zookeeper;

import java.nio.file.Path;
import java.util.Properties;


public class ZookeeperPropsBuilder {

    private Properties props = new Properties();

    public ZookeeperPropsBuilder tickTime(Long tickTimeMs) {
        props.setProperty("tickTime", tickTimeMs.toString());
        return this;
    }

    public ZookeeperPropsBuilder clientPort(Integer clientPort) {
        props.setProperty("clientPort", clientPort.toString());
        return this;
    }

    public ZookeeperPropsBuilder initLimit(Integer initLimit) {
        props.setProperty("initLimit", initLimit.toString());
        return this;
    }

    public ZookeeperPropsBuilder syncLimit(Integer syncLimit) {
        props.setProperty("syncLimit", syncLimit.toString());
        return this;
    }

    public ZookeeperPropsBuilder dataDir(String dataDir) {
        props.setProperty("dataDir", dataDir);
        return this;
    }

    public Properties toProps() {
        return props;
    }

    public static ZookeeperPropsBuilder create() {
        return new ZookeeperPropsBuilder();
    }

    public static ZookeeperPropsBuilder buildDefault(Integer port) {
        Path dataDirPath = ZookeeperPropertyUtils.createTempZookeeperDataDir();
        return new ZookeeperPropsBuilder().
            dataDir(dataDirPath.toString()).
            tickTime(2000L).
            clientPort(port).
            initLimit(5).
            syncLimit(2);
    }

    public static ZookeeperPropsBuilder buildDefaultOnRandomPort() {
        Integer randomPort = ZookeeperPropertyUtils.getRandomPort();
        return buildDefault(randomPort);
    }

    public static ZookeeperPropsBuilder buildDefault() {
        return buildDefault(2181);
    }

    public Properties buildWithCustom(Properties customProps) {
        Properties properties = new Properties();
        customProps.putAll(props);
        customProps.putAll(customProps);
        return properties;
    }

    public static ZookeeperPropsBuilder buildWithCustomOnly(Properties customProps) {
        ZookeeperPropsBuilder builder = new ZookeeperPropsBuilder();
        builder.props = customProps;
        return builder;
    }

}
