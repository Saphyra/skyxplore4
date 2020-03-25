package com.github.saphyra.skyxplore.test.framework;

import static com.github.saphyra.skyxplore.test.framework.SleepUtil.sleep;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VerifiedOperation {
    public static <T> T getWithWait(Fetcher<T> fetcher) {
        waitUntil(fetcher);
        return fetcher.fetch();
    }

    public static boolean operate(Operation operation) {
        return operate(operation, 10, 500);
    }

    public static boolean operate(Operation operation, int tryCount, int sleep) {
        operation.execute();
        return waitUntil(operation, tryCount, sleep);
    }

    public static boolean waitUntil(Checker operation) {
        return waitUntil(operation, 10, 500);
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
}
