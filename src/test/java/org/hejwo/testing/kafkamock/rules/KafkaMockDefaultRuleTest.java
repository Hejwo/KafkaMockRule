package org.hejwo.testing.kafkamock.rules;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class KafkaMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 2000;

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    @Test
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        Thread.sleep(SERVER_RUNTIME);
    }
}