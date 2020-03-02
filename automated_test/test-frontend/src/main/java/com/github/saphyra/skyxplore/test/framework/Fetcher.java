package com.github.saphyra.skyxplore.test.framework;

public interface Fetcher<T> extends Checker {
    T fetch();
}
