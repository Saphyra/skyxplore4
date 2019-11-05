package com.github.saphyra.skyxplore.game.module.system.costruction.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConstructionRepository extends JpaRepository<ConstructionEntity, String> {
}
