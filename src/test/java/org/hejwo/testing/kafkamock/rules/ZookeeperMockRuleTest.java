package org.hejwo.testing.kafkamock.rules;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZookeeperMockRuleTest {

    @ClassRule
    public static ZookeeperMockRule zookeeperMockRule = ZookeeperMockRule.create();

    @Test
    public void shouldRunZookeeperMock() throws Exception {
        Thread.sleep(1000);
    }

}