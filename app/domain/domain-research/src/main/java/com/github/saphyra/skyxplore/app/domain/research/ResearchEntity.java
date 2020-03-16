package com.github.saphyra.skyxplore.app.domain.research;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "research")
class ResearchEntity implements SettablePersistable<String> {
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
