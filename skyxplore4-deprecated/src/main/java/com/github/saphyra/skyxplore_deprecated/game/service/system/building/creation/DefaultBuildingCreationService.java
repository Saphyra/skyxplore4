package com.github.saphyra.skyxplore_deprecated.game.service.system.building.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.data.AbstractDataService;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore_deprecated.game.common.DistanceCalculator;
import com.github.saphyra.skyxplore_deprecated.game.common.DomainSaverService;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.building.Building;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultBuildingCreationService {
    private static final List<BuildingData> EXCAVATOR_BUILDINGS = Arrays.asList(
        new ExcavatorBuilding(SurfaceType.ORE_MINE),
        new ExcavatorBuilding(SurfaceType.MOUNTAIN)
    );

    private final DistanceCalculator distanceCalculator;
    private final DomainSaverService domainSaverService;
    private final List<AbstractDataService<?, ? extends BuildingData>> buildingDataServices;
    private final BuildingFactory buildingFactory;

    public void addDefaultBuildingsForSystem(List<Surface> allSurfaces) {
        List<BuildingData> defaultBuildings = getDefaultBuildings();
        log.debug("Default buildings to place: {}", defaultBuildings);

        List<Building> buildings = allSurfaces.stream()
            .collect(Collectors.groupingBy(Surface::getStarId))
            .values()
            .stream()
            .flatMap(surfaces -> placeBuildings(surfaces, defaultBuildings))
            .collect(Collectors.toList());
        domainSaverService.addAll(buildings);
    }

    private Stream<Building> placeBuildings(List<Surface> surfaces, List<BuildingData> defaultBuildings) {
        List<Building> buildings = new ArrayList<>();
        defaultBuildings.forEach(buildingData -> place(buildingData, surfaces, buildings));
        return buildings.stream();
    }

    private List<BuildingData> getDefaultBuildings() {
        return Stream.concat(
            buildingDataServices.stream()
                .flatMap(abstractDataService -> abstractDataService.values()
                    .stream()
                    .filter(BuildingData::isDefaultBuilding)
                ),
            EXCAVATOR_BUILDINGS.stream()
        ).collect(Collectors.toList());
    }

    private void place(BuildingData buildingData, List<Surface> surfaces, List<Building> buildings) {
        log.debug("Placing building {}", buildingData.getId());
        Surface surface = getSurfaceForType(buildingData.getPrimarySurfaceType(), surfaces, buildings);
        log.debug("Selected surface: {}", surface.getCoordinate());
        buildings.add(buildingFactory.create(
            buildingData.getId(),
            surface.getGameId(),
            surface.getStarId(),
            surface.getSurfaceId(),
            surface.getPlayerId()
        ));
    }

    private Surface getSurfaceForType(SurfaceType surfaceType, List<Surface> surfaces, List<Building> buildings) {
        List<Surface> surfacesWithMatchingType = surfaces.stream()
            .filter(surface -> surface.getSurfaceType().equals(surfaceType))
            .collect(Collectors.toList());

        if (surfacesWithMatchingType.isEmpty()) {
            log.debug("There is no surface with type {}", surfaceType);
            Surface randomEmptySurface = getRandomEmptySurface(surfaces, buildings);
            randomEmptySurface.setSurfaceType(surfaceType);
            return randomEmptySurface;
        }

        Optional<Surface> emptySurfaceWithMatchingType = surfacesWithMatchingType.stream()
            .filter(surface -> isEmpty(surface.getSurfaceId(), buildings))
            .findFirst();
        if (emptySurfaceWithMatchingType.isPresent()) {
            log.debug("Empty surface found for surfaceType {}: {}", surfaceType, emptySurfaceWithMatchingType);
            return emptySurfaceWithMatchingType.get();
        }

        Surface randomEmptySurfaceNextToType = getRandomEmptySurfaceNextTo(surfacesWithMatchingType, surfaces, buildings);
        randomEmptySurfaceNextToType.setSurfaceType(surfaceType);
        log.debug("Random empty surface next to surfaceType {}: {}", surfaceType, randomEmptySurfaceNextToType);
        return randomEmptySurfaceNextToType;
    }

    private Surface getRandomEmptySurface(List<Surface> surfaces, List<Building> buildings) {
        return surfaces.stream()
            .filter(surface -> surface.getSurfaceType().equals(SurfaceType.DESERT))
            .filter(surface -> isEmpty(surface.getSurfaceId(), buildings))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("There are no empty surfaces left."));
    }

    private Surface getRandomEmptySurfaceNextTo(List<Surface> surfacesWithMatchingType, List<Surface> surfaces, List<Building> buildings) {
        for (Surface surface : surfacesWithMatchingType) {
            Optional<Surface> emptySurface = getEmptySurfaceNextTo(surface.getCoordinate(), surfaces, buildings);
            if (emptySurface.isPresent()) {
                return emptySurface.get();
            }
        }

        throw new IllegalStateException("No empty surface found next to the surfaces with matching type.");
    }

    private Optional<Surface> getEmptySurfaceNextTo(Coordinate coordinate, List<Surface> surfaces, List<Building> buildings) {
        for (Surface surface : surfaces) {
            if (isEmpty(surface.getSurfaceId(), buildings) && isNextTo(coordinate, surface.getCoordinate())) {
                return Optional.of(surface);
            }
        }
        return Optional.empty();
    }

    private boolean isEmpty(UUID surfaceId, List<Building> buildings) {
        return buildings.stream()
            .noneMatch(building -> building.getSurfaceId().equals(surfaceId));
    }

    private boolean isNextTo(Coordinate c1, Coordinate c2) {
        return distanceCalculator.getDistance(c1, c2) == 1;
    }

    private static class ExcavatorBuilding extends BuildingData {
        private static final String EXCAVATOR_ID = "excavator";

        private final SurfaceType surfaceType;

        private ExcavatorBuilding(SurfaceType surfaceType) {
            setId(EXCAVATOR_ID);
            this.surfaceType = surfaceType;
        }

        @Override
        public List<SurfaceType> getPlaceableSurfaceTypes() {
            return Arrays.asList(surfaceType);
        }

        @Override
        public SurfaceType getPrimarySurfaceType() {
            return surfaceType;
        }
    }
}
