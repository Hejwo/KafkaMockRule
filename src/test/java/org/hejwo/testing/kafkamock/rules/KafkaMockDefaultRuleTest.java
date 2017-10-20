package org.hejwo.testing.kafkamock.rules;

import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.*;

public class KafkaMockDefaultRuleTest {

    private static final int SERVER_RUNTIME = 32000;

    @ClassRule
    public static KafkaMockRule kafkaMockRule = KafkaMockRule.create();

    @Test
    public void shouldRunKafkaAndZookeeper() throws InterruptedException {
        System.out.println("Zookeeper running ? "+kafkaMockRule.isZookeeperRunning());
        System.out.println("Kafka running ? "+kafkaMockRule.isKafkaRunning());
//        Thread.sleep(SERVER_RUNTIME);
    }
}