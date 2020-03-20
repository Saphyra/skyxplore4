package com.github.saphyra.skyxplore.app.domain.star_connection;

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
@Table(name = "star_connection")
class StarConnectionEntity implements SettablePersistable<String> {
    @Id
    private String connectionId;
    private String gameId;
    private String star1;
    private String star2;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return connectionId;
    }
}
