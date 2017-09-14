package org.hejwo.testing.kafkamock.general.utils;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        String message = format("Exception on thread %s", t);
        log.error("Err !!!",e);
        throw new RuntimeException(message, e);
    }
}
