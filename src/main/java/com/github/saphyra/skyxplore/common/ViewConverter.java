package com.github.saphyra.skyxplore.common;

import java.util.List;
import java.util.stream.Collectors;

public interface ViewConverter<S, T> {
    T convertDomain(S domain);

    default List<T> convertDomain(List<S> domain) {
        return domain.stream()
            .map(this::convertDomain)
            .collect(Collectors.toList());
    }
}
