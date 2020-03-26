package com.github.saphyra.skyxplore.test.framework;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.github.saphyra.skyxplore.test.framework.SleepUtil.sleep;

@UtilityClass
@Slf4j
public class VerifiedOperation {
    public static <T> T getWithWait(Fetcher<T> fetcher) {
        waitUntil(fetcher);
        return fetcher.fetch();
    }

    public static boolean operate(Operation operation) {
        return operate(operation, 60, 500);
    }

    public static boolean operate(Operation operation, int tryCount, int sleep) {
        operation.execute();
        return waitUntil(operation, tryCount, sleep);
    }

    public static boolean waitUntil(Checker operation) {
        return waitUntil(operation, 60, 500);
    }

    public static boolean waitUntil(Checker operation, int tryCount, int sleep) {
        for (int run = 0; run < tryCount; run++) {
            if (operation.check()) {
                return true;
            }
            sleep(sleep);
        }
        return false;
    }

    public static void operateRetry(Operation operation, int retryCount, int tryCount, int sleep) {
        for (int i = 0; i < retryCount; i++) {
            operation.execute();
            if (waitUntil(operation, tryCount, sleep)) {
                return;
            }
            log.info("Operation failed for try {}.", i);
        }
    }
}
