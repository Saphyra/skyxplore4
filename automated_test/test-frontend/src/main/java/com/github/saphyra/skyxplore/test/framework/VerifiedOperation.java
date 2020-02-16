package com.github.saphyra.skyxplore.test.framework;

import static com.github.saphyra.skyxplore.test.framework.SleepUtil.sleep;

public class VerifiedOperation {
    public static boolean operate(Operation operation, int tryCount, int sleep) {
        operation.execute();
        if (waitUntil(operation, tryCount, sleep)) return true;
        return false;
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
