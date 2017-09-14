package org.hejwo.testing.kafkamock.rules;

import java.util.Properties;

import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.hejwo.testing.kafkamock.general.KafkaLocalThread;
import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;

import static org.hejwo.testing.kafkamock.general.utils.KafkaPropertyUtils.createDefaultKafkaProperties;
import static org.hejwo.testing.kafkamock.general.utils.ZookeeperPropertyUtils.createDefaultZookeeperProperties;

@Slf4j
public class KafkaMockRule extends ExternalResource {

    private final ZookeeperLocalThread zookeeperLocalThread;
    private final KafkaLocalThread kafkaLocalThread;

    public static KafkaMockRule create(Properties kafkaProperties, Properties zookeeperProperties) {
        return new KafkaMockRule(kafkaProperties, zookeeperProperties);
    }

    public static KafkaMockRule create(Properties kafkaProperties) {
        log.debug("Created default Zookeeper rule");
        return new KafkaMockRule(kafkaProperties, createDefaultZookeeperProperties());
    }

    public static KafkaMockRule create() {
        return new KafkaMockRule(createDefaultKafkaProperties(), createDefaultZookeeperProperties());
    }

    private KafkaMockRule(Properties kafkaProperties, Properties zookeeperProperties) {
        this.zookeeperLocalThread = new ZookeeperLocalThread(zookeeperProperties);
        this.kafkaLocalThread = new KafkaLocalThread(kafkaProperties);
    }

    @Override
    protected void before() throws Throwable {
        if(!zookeeperLocalThread.isAlive()) {
            zookeeperLocalThread.start();
        }
        if(!kafkaLocalThread.isAlive()) {
            kafkaLocalThread.start();
        }
    }

    @Override
    protected void after() {
        zookeeperLocalThread.stop();
        kafkaLocalThread.stop();
    }

}
