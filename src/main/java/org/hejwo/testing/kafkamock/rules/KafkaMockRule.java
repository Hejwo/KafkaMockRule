package org.hejwo.testing.kafkamock.rules;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.hejwo.testing.kafkamock.general.KafkaLocalThread;
import org.hejwo.testing.kafkamock.general.ZookeeperLocalThread;
import org.hejwo.testing.kafkamock.general.properties.zookeeper.ZookeeperPropertiesBuilder;
import org.junit.rules.ExternalResource;

import lombok.extern.slf4j.Slf4j;

import static org.hejwo.testing.kafkamock.general.utils.KafkaPropertyUtils.createDefaultKafkaProperties;

@Slf4j
public class KafkaMockRule extends ExternalResource {

    private final static long DEFAULT_START_TIME_MS = 1500;

    private final ExecutorService executor;

    private final long startTime;
    private final ZookeeperLocalThread zookeeperLocalThread;
    private final KafkaLocalThread kafkaLocalThread;

    private Future<?> zookeeperTask;
    private Future<?> kafkaTask;

    public static KafkaMockRule create(Properties kafkaProperties, ZookeeperPropertiesBuilder zookeeperPropBuilder) {
        return new KafkaMockRule(kafkaProperties, zookeeperPropBuilder, DEFAULT_START_TIME_MS);
    }

    public static KafkaMockRule create(Properties kafkaProperties) {
        log.debug("Created default Zookeeper rule");
        return new KafkaMockRule(kafkaProperties, ZookeeperPropertiesBuilder.buildDefault(), DEFAULT_START_TIME_MS);
    }

    public static KafkaMockRule create() {
        return new KafkaMockRule(createDefaultKafkaProperties(), ZookeeperPropertiesBuilder.buildDefault(), DEFAULT_START_TIME_MS);
    }

    public static KafkaMockRule create(long startupTimeMs) {
        return new KafkaMockRule(createDefaultKafkaProperties(), ZookeeperPropertiesBuilder.buildDefault(), startupTimeMs);
    }

    private KafkaMockRule(Properties kafkaProperties, ZookeeperPropertiesBuilder zookeeperPropBuilder, long startTime) {
        executor = new ForkJoinPool();

        this.zookeeperLocalThread = new ZookeeperLocalThread(zookeeperPropBuilder);
        this.kafkaLocalThread = new KafkaLocalThread(kafkaProperties);
        this.startTime = startTime;
    }

    public KafkaLookup lookup() {
        return new KafkaLookup();
    }

    @Override
    protected void before() throws Throwable {
        zookeeperTask = executor.submit(zookeeperLocalThread);
        kafkaTask = executor.submit(kafkaLocalThread);
        Thread.sleep(1000);
    }

    @Override
    protected void after() {
        try {
            executor.submit(createTerminationTask());
            executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            log.warn("Force shutdown made after 1000 millis.");
        } catch (Exception ex) {
            log.error("Task exited with error", ex);
        }
    }

    private Runnable createTerminationTask() {
        return new Runnable() {
            @Override
            public void run() {
                zookeeperLocalThread.shutdown();
                kafkaLocalThread.shutdown();
            }
        };
    }

    public boolean isZookeeperRunning() {
        return !zookeeperTask.isDone();
    }

    public boolean isKafkaRunning() {
        return !kafkaTask.isDone();
    }

}
