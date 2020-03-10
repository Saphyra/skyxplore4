package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//TODO unit test
public class ProcessDeletionsComponent {
    private static final int MAX_CHUNK_SIZE = 10000;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void processDeletions(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository) {
        log.info("Processing deletions for entity {}...", repository.getEntityName());
        Set<ID> deleteQueue = repository.getDeleteQueue();
        ArrayList<ID> ids = new ArrayList<>(deleteQueue);
        chunks(ids).forEach(repository::deleteByIds);
        deleteQueue.clear();
        log.info("Deletion finished for entity {}. Number of deleted entities: {}", repository.getEntityName(), ids.size());
    }

    private static <T> List<List<T>> chunks(List<T> bigList) {
        List<List<T>> chunks = new ArrayList<>();

        for (int i = 0; i < bigList.size(); i += MAX_CHUNK_SIZE) {
            List<T> chunk = bigList.subList(i, Math.min(bigList.size(), i + MAX_CHUNK_SIZE));
            chunks.add(chunk);
        }
        log.debug("Number of entities {} was chunked to {} parts.", bigList.size(), chunks.size());
        return chunks;
    }
}
