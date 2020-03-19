package com.github.saphyra.skyxplore.app.common.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class ExecutorServiceBean implements Executor {
    private final Executor executor = Executors.newCachedThreadPool();


    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }
}
