package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

    @Override
    public boolean isNew() {
        return false;
    }
}
