package org.hejwo.testing.kafkamock.rules;

import java.io.IOException;
import java.net.Socket;

import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hejwo.testing.kafkamock.TestUtils.isPortAvailable;

public class ZookeeperMockCustomPortRuleTest {

    private static final int SERVER_RUNTIME = 2000;
    private static final int CUSTOM_PORT = 9919;

    @ClassRule
    public static ZookeeperMockRule zookeeperMockRule = ZookeeperMockRule.create(CUSTOM_PORT);

    @Test
    public void shouldRunZookeeperMock() throws Exception {
        assertThat(zookeeperMockRule.isRunning()).isTrue();
        Thread.sleep(SERVER_RUNTIME);
        assertThat(isPortAvailable(CUSTOM_PORT)).isFalse();

        zookeeperMockRule.getZookeeperLocalThread().shutdown();
        assertThat(zookeeperMockRule.isRunning()).isFalse();
        assertThat(isPortAvailable(CUSTOM_PORT)).isTrue();
    }

}