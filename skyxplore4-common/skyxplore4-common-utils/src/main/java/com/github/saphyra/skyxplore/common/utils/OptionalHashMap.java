package com.github.saphyra.skyxplore.common.utils;

import com.github.saphyra.util.OptionalMap;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class OptionalHashMap<K, V> extends HashMap<K, V> implements OptionalMap<K, V> {
    public OptionalHashMap(Map<K, V> s) {
        super(s);
    }
}
