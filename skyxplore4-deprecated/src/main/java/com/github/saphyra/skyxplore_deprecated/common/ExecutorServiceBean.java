package com.github.saphyra.skyxplore_deprecated.common;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class ExecutorServiceBean implements Executor {
    private final Executor executor = Executors.newCachedThreadPool();


    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }
}
