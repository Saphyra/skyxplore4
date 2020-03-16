package com.github.saphyra.skyxplore.app.domain.research;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class ResearchQueryService {
    private final RequestContextHolder requestContextHolder;
    private final ResearchDao researchDao;

    public List<Research> getByStarIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return researchDao.getByStarIdAndPlayerId(starId, playerId);
    }
}
