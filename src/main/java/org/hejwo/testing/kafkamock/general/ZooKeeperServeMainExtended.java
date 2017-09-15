package org.hejwo.testing.kafkamock.general;

import org.apache.zookeeper.server.ZooKeeperServerMain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZooKeeperServeMainExtended extends ZooKeeperServerMain {

    @Override
    public void shutdown() {
        try {
            super.shutdown();
        } catch(NullPointerException ex) {
            log.error("!!! Zookeeper process is not running. Failed to shutdown");
            //silent quit of shutdown in case of process not running at all
        }
    }
}
