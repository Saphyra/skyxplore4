package com.github.saphyra.skyxplore.test.framework;

import static com.github.saphyra.skyxplore.test.framework.SleepUtil.sleep;

public class VerifiedOperation {
    public static boolean operate(Operation operation, int tryCount, int sleep) {
        operation.execute();
        for (int run = 0; run < tryCount; run++) {
            if (operation.check()) {
                return true;
            }
            sleep(sleep);
        }
        return false;
    }
}
