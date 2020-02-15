package com.github.saphyra.skyxplore.test.framework;

import org.springframework.stereotype.Component;

@Component
public class UrlProvider {
    public static String getRoot(int port) {
        return String.format("http://localhost:%s", port);
    }

    public static String getWebRoot(int port) {
        return getRoot(port) + "/web";
    }
}
