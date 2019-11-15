package com.github.saphyra.skyxplore.game.service.system.building.creation;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.common.DistanceCalculator;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultBuildingCreationService {
    private static final List<BuildingData> EXCAVATOR_BUILDINGS = Arrays.asList(
            new ExcavatorBuilding(SurfaceType.ORE_MINE),
            new ExcavatorBuilding(SurfaceType.MOUNTAIN)
    );

    private final BuildingDao buildingDao;
    private final DistanceCalculator distanceCalculator;
    private final List<AbstractDataService<?, ? extends BuildingData>> buildingDataServices;
    private final BuildingFactory buildingFactory;

    public void addDefaultBuildingsForSystem(List<Surface> surfaces) {
        List<BuildingData> defaultBuildings = getDefaultBuildings();
        log.debug("Default buildings to place: {}", defaultBuildings);

        List<Building> buildings = defaultBuildings.stream()
                .map(buildingData -> place(buildingData, surfaces))
                .collect(Collectors.toList());
        buildingDao.saveAll(buildings);
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

    private Building place(BuildingData buildingData, List<Surface> surfaces) {
        log.debug("Placing building {}", buildingData.getId());
        Surface surface = getSurfaceForType(buildingData.getPrimarySurfaceType(), surfaces);
        log.debug("Selected surface: {}", surface.getCoordinate());
        Building building = buildingFactory.create(buildingData.getId(), surface.getGameId(), surface.getUserId(), surface.getStarId());
        surface.setBuildingId(building.getBuildingId());
        return building;
    }

    private Surface getSurfaceForType(SurfaceType surfaceType, List<Surface> surfaces) {
        List<Surface> surfacesWithMatchingType = surfaces.stream()
                .filter(surface -> surface.getSurfaceType().equals(surfaceType))
                .collect(Collectors.toList());

        if (surfacesWithMatchingType.isEmpty()) {
            log.debug("There is no surface with type {}", surfaceType);
            Surface randomEmptySurface = getRandomEmptySurface(surfaces);
            randomEmptySurface.setSurfaceType(surfaceType);
            return randomEmptySurface;
        }

        Optional<Surface> emptySurfaceWithMatchingType = surfacesWithMatchingType.stream()
                .filter(Surface::isEmpty)
                .findFirst();
        if (emptySurfaceWithMatchingType.isPresent()) {
            log.debug("Empty surface found for surfaceType {}: {}", surfaceType, emptySurfaceWithMatchingType);
            return emptySurfaceWithMatchingType.get();
        }

        Surface randomEmptySurfaceNextToType = getRandomEmptySurfaceNextTo(surfacesWithMatchingType, surfaces);
        randomEmptySurfaceNextToType.setSurfaceType(surfaceType);
        log.debug("Random empty surface next to surfaceType {}: {}", surfaceType, randomEmptySurfaceNextToType);
        return randomEmptySurfaceNextToType;
    }

    private Surface getRandomEmptySurface(List<Surface> surfaces) {
        return surfaces.stream()
                .filter(Surface::isEmpty)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("There are no empty surfaces left."));
    }

    private Surface getRandomEmptySurfaceNextTo(List<Surface> surfacesWithMatchingType, List<Surface> surfaces) {
        for (Surface surface : surfacesWithMatchingType) {
            Optional<Surface> emptySurface = getEmptySurfaceNextTo(surface.getCoordinate(), surfaces);
            if (emptySurface.isPresent()) {
                return emptySurface.get();
            }
        }

        throw new IllegalStateException("No empty surface found next to the surfaces with matching type.");
    }

    private Optional<Surface> getEmptySurfaceNextTo(Coordinate coordinate, List<Surface> surfaces) {
        for (Surface surface : surfaces) {
            if (surface.isEmpty() && isNextTo(coordinate, surface.getCoordinate())) {
                return Optional.of(surface);
            }
        }
        return Optional.empty();
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
