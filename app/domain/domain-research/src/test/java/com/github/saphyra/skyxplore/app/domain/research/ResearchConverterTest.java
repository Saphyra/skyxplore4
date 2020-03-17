package com.github.saphyra.skyxplore.app.domain.research;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class ResearchConverterTest {
    private static final String RESEARCH_ID_STRING = "research-id";
    private static final String STAR_ID_STRING = "star-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String PLAYER_ID_STRING = "player-id";
    private static final String DATA_ID = "data-id";
    private static final UUID RESEARCH_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private ResearchConverter underTest;

    @Test
    public void convertEntity() {
        given(uuidConverter.convertEntity(RESEARCH_ID_STRING)).willReturn(RESEARCH_ID);
        given(uuidConverter.convertEntity(STAR_ID_STRING)).willReturn(STAR_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(PLAYER_ID_STRING)).willReturn(PLAYER_ID);

        ResearchEntity entity = ResearchEntity.builder()
            .researchId(RESEARCH_ID_STRING)
            .starId(STAR_ID_STRING)
            .gameId(GAME_ID_STRING)
            .playerId(PLAYER_ID_STRING)
            .dataId(DATA_ID)
            .isNew(true)
            .build();

        Research result = underTest.convertEntity(entity);

        assertThat(result.getResearchId()).isEqualTo(RESEARCH_ID);
        assertThat(result.getStarId()).isEqualTo(STAR_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getDataId()).isEqualTo(DATA_ID);
        assertThat(result.isNew()).isFalse();
    }

    @Test
    public void convertDomain() {
        given(uuidConverter.convertDomain(RESEARCH_ID)).willReturn(RESEARCH_ID_STRING);
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        Research domain = Research.builder()
            .researchId(RESEARCH_ID)
            .starId(STAR_ID)
            .gameId(GAME_ID)
            .playerId(PLAYER_ID)
            .dataId(DATA_ID)
            .isNew(true)
            .build();

        ResearchEntity result = underTest.convertDomain(domain);

        assertThat(result.getResearchId()).isEqualTo(RESEARCH_ID_STRING);
        assertThat(result.getStarId()).isEqualTo(STAR_ID_STRING);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID_STRING);
        assertThat(result.getDataId()).isEqualTo(DATA_ID);
        assertThat(result.isNew()).isTrue();
    }
}