package org.hejwo.testing.kafkamock.rules;

import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hejwo.testing.kafkamock.TestUtils.isPortAvailable;

public class ZookeeperMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 2000;

    @ClassRule
    public static ZookeeperMockRule zookeeperMockRule = ZookeeperMockRule.create();

    @Test
    public void shouldRunZookeeperMock() throws Exception {

        assertThat(zookeeperMockRule.isRunning()).isTrue();
        Thread.sleep(SERVER_RUNTIME);
        assertThat(isPortAvailable(zookeeperMockRule.getPort())).isFalse();

        zookeeperMockRule.getZookeeperLocalThread().shutdown();
        assertThat(zookeeperMockRule.isRunning()).isFalse();
        assertThat(isPortAvailable(zookeeperMockRule.getPort())).isTrue();
    }

}