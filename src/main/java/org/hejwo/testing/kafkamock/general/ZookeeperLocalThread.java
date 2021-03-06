package org.hejwo.testing.kafkamock.general;

import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.hejwo.testing.kafkamock.general.properties.zookeeper.ZookeeperPropsBuilder;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public class ZookeeperLocalThread extends Thread {

    private final ServerConfig configuration;
    private final ZooKeeperServeMainExtended zookeeperServer;
    private final ZookeeperPropsBuilder zookeeperPropsBuilder;

    public ZookeeperLocalThread(ZookeeperPropsBuilder zookeeperPropsBuilder) {
        this.setName("KafkaMockRule-ZookeeperThread");
        this.zookeeperPropsBuilder = zookeeperPropsBuilder;
        this.configuration = prepareConfiguration(zookeeperPropsBuilder.toProps());
        this.zookeeperServer = new ZooKeeperServeMainExtended();
    }

    private ServerConfig prepareConfiguration(Properties zookeeperProperties) {
        try {
            QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
            quorumConfiguration.parseProperties(zookeeperProperties);

            final ServerConfig configuration = new ServerConfig();
            configuration.readFrom(quorumConfiguration);
            return configuration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String zookeeperConfigToString(ServerConfig config) {
        return format("clientPortAddress=%s, dataDir=%s, dataLogDir=%s, maxClientCnxns=%s, maxSessionTimeout=%s, minSessionTimeout=%s, tickTime=%s",
            config.getClientPortAddress(), config.getDataDir(), config.getDataLogDir(),
            config.getMaxClientCnxns(), config.getMaxSessionTimeout(), config.getMinSessionTimeout(),
            config.getTickTime());
    }

    @Override
    public void run() {
        try {
            log.info("Starting Zookeeper");
            log.debug("Zookeeper serverConfig: {{}}", zookeeperConfigToString(configuration));
            zookeeperServer.runFromConfig(configuration);
            System.out.println("bbb");
        } catch (IOException e) {
            throw new RuntimeException("Failed to start Zookeeper", e);
        }
    }

    public ZookeeperPropsBuilder getZookeeperPropsBuilder() {
        return zookeeperPropsBuilder;
    }

    public void shutdown() {
        zookeeperServer.shutdown();
        this.interrupt();
        // this delete /tmp/zookeeper
    }

    public ZooKeeperServeMainExtended getZookeeperServer() {
        return zookeeperServer;
    }

    public Integer getPort() {
        return configuration.getClientPortAddress().getPort();
    }
}
