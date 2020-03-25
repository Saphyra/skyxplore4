package com.github.saphyra.skyxplore.app.common.service;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.github.saphyra.skyxplore.app.common.event.EntitiesSavedEvent;
import com.github.saphyra.skyxplore.app.common.game_context.SaveAllDao;
import com.github.saphyra.skyxplore.app.common.utils.OptionalHashMap;
import com.github.saphyra.util.OptionalMap;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DomainSaverService {
    private final ThreadLocal<Map<Class<?>, Set<Object>>> tempStorage = new ThreadLocal<>();

    private final ApplicationEventPublisher applicationEventPublisher;
    private final OptionalMap<Class<?>, SaveAllDao<?>> daoMap;

    public DomainSaverService(ApplicationEventPublisher applicationEventPublisher, List<SaveAllDao<?>> daos) {
        this.applicationEventPublisher = applicationEventPublisher;
        daoMap = new OptionalHashMap<>(daos.stream()
            .collect(Collectors.toMap(SaveAllDao::getType, Function.identity())));
        log.info("SaveAllDaos mapped. Found types: {}", daoMap.keySet());
    }

    public void add(@NonNull Object o) {
        log.debug("Adding item to cache: {}", o);
        if (o instanceof Collection) {
            throw new IllegalArgumentException("Domain must not be a collection. Use addAll instead. Collection: " + o.toString());
        }
        getDao(o.getClass());
        getList(o.getClass()).add(o);
    }

    public void addAll(@NonNull Collection<?> o) {
        log.debug("Adding items to cache: {}", o);
        if (o.isEmpty()) {
            return;
        }
        Class<?> type = getType(o);
        getDao(type);
        getList(type).addAll(o);
    }

    private Class<?> getType(Collection<?> o) {
        return o.iterator().next().getClass();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void save() {
        try {
            log.info("Saving cached domains...");
            Optional.ofNullable(tempStorage.get())
                .ifPresent(classListMap -> classListMap.forEach(this::save));
            log.info("Save process successfully finished.");
            applicationEventPublisher.publishEvent(new EntitiesSavedEvent());
        } finally {
            log.info("Clearing cache...");
            clear();
        }
    }

    public void clear() {
        tempStorage.remove();
    }

    @SuppressWarnings("unchecked")
    private void save(Class<?> type, Set<Object> domains) {
        StopWatch stopWatch = new StopWatch(String.format("%s number of %s", domains.size(), type));
        stopWatch.start();
        getDao(type).saveAll(new ArrayList<>(domains));
        stopWatch.stop();
        log.info("{} was/were saved in {}ms", stopWatch.getId(), stopWatch.getTotalTimeMillis());
    }

    @SuppressWarnings("rawtypes")
    private SaveAllDao getDao(Class<?> type) {
        return daoMap.getOptional(type)
            .orElseThrow(() -> new IllegalStateException("No dao found for type {}" + type.getName()));
    }

    private Set<Object> getList(Class<?> type) {
        if (isNull(tempStorage.get())) {
            tempStorage.set(new HashMap<>());
        }

        Map<Class<?>, Set<Object>> map = tempStorage.get();
        if (!map.containsKey(type)) {
            map.put(type, new HashSet<>());
        }
        return map.get(type);
    }
}
