package org.hejwo.testing.kafkamock.rules;

import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hejwo.testing.kafkamock.TestUtils.TEST_TIMEOUT;
import static org.hejwo.testing.kafkamock.TestUtils.isPortAvailable;

public class ZookeeperMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 2000;

    @ClassRule
    public static ZookeeperMockRule zookeeperMockRule = ZookeeperMockRule.create();

    @Test(timeout = TEST_TIMEOUT)
    public void shouldRunZookeeperMock() throws Exception {

        assertThat(zookeeperMockRule.isRunning()).isTrue();
        Thread.sleep(SERVER_RUNTIME);
        assertThat(isPortAvailable(zookeeperMockRule.getPort())).isFalse();

        zookeeperMockRule.getZookeeperLocalThread().shutdown();
        assertThat(zookeeperMockRule.isRunning()).isFalse();
        assertThat(isPortAvailable(zookeeperMockRule.getPort())).isTrue();
    }

}