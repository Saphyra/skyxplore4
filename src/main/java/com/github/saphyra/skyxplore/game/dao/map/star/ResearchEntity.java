package com.github.saphyra.skyxplore.game.dao.map.star;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "research")
class ResearchEntity implements Persistable<String> {
    @Id
    private String researchId;
    private String gameId;
    private String playerId;
    private String starId;
    private String dataId;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return researchId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
