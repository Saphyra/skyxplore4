package com.github.saphyra.skyxplore.test.framework;

import com.github.saphyra.skyxplore.test.common.TestBase;

public class UrlFactory {
    public static String assemble(String uri){
        return String.format("http://localhost:%s%s", TestBase.PORT, uri);
    }
}
