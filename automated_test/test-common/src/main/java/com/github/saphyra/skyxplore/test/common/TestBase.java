package com.github.saphyra.skyxplore.test.common;

import com.github.saphyra.skyxplore.server.Application;
import com.github.saphyra.util.Random;
import org.testng.TestException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static java.util.Objects.isNull;

public class TestBase {
    public static final int PORT = new Random().randInt(9000, 10000);

    private final String[] ARGS = new String[]{
        "--spring.profiles.active=test",
        "--server.port=" + PORT
    };

    @BeforeSuite(alwaysRun = true)
    public void startServer() {
        try {
            Application.main(ARGS);
        } catch (Throwable e) {
            throw new TestException("Application startup failed.", e);
        }
    }

    @AfterSuite(alwaysRun = true)
    public void stopServer() {
        if (!isNull(Application.applicationContext)) {
            Application.applicationContext.stop();
        }
    }
}
