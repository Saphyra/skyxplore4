package com.github.saphyra.skyxplore.app.common.utils;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Mapping<K, V> {
    private final K key;
    private final V value;
}
