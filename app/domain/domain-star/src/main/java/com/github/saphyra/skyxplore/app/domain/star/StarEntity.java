package com.github.saphyra.skyxplore.app.domain.star;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "star")
@NoArgsConstructor
class StarEntity implements SettablePersistable<String> {
    @Id
    private String starId;
    private String gameId;
    private String starName;
    @Embedded
    private CoordinateEntity coordinates;
    private String ownerId;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return starId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
