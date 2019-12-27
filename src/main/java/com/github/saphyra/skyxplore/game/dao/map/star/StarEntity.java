package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "star")
@NoArgsConstructor
class StarEntity implements Persistable<String> {
    @Id
    private String starId;
    private String gameId;
    private String userId;
    private String starName;
    @Embedded
    private CoordinateEntity coordinates;
    private String ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "star_id")
    private List<ResearchEntity> researches;

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
