package com.github.saphyra.skyxplore.game.dao.system.priority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
class PriorityEntityId implements Serializable {
    private String starId;
    @Enumerated(EnumType.STRING)
    private PriorityType type;
}
