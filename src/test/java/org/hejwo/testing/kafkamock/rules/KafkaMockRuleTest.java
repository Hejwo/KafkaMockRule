package org.hejwo.testing.kafkamock.rules;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class KafkaMockRuleTest {

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    @Test
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        Thread.sleep(2000);
    }
}