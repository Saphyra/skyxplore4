package com.github.saphyra.skyxplore.app.common.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class CollectionUtil {
    public <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public <T> List<List<T>> chunks(List<T> bigList, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();

        for (int i = 0; i < bigList.size(); i += chunkSize) {
            List<T> chunk = bigList.subList(i, Math.min(bigList.size(), i + chunkSize));
            chunks.add(chunk);
        }
        log.debug("Number of entities {} was chunked to {} parts.", bigList.size(), chunks.size());
        return chunks;
    }
}
