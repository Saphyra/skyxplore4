package com.github.saphyra.skyxplore_deprecated.game.service.map.surface.creation;

import com.github.saphyra.skyxplore_deprecated.game.common.DomainSaverService;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.coordinate.Coordinate;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.Star;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.service.system.building.creation.DefaultBuildingCreationService;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurfaceCreationService {
    private final DefaultBuildingCreationService defaultBuildingCreationService;
    private final DomainSaverService domainSaverService;
    private final Random random;
    private final SurfaceFactory surfaceFactory;
    private final SurfaceCreationProperties properties;

    public void createSurfaces(List<Star> stars) {
        log.info("Creating surfaces...");
        List<Surface> surfaces = stars.stream()
            .parallel()
            .flatMap(this::createSurfaces)
            .collect(Collectors.toList());
        log.info("Number of surfaces created: {}", surfaces.size());
        defaultBuildingCreationService.addDefaultBuildingsForSystem(surfaces);
        domainSaverService.addAll(surfaces);
    }

    private Stream<Surface> createSurfaces(Star star) {
        log.debug("Creating surfaces for star {}", star.getStarId());
        SurfaceType[][] surfaceMap = createSurfaceMap();
        log.debug("Surfaces created for star {}", star.getStarId());
        List<Surface> surfaces = mapSurfaces(surfaceMap, star);
        return surfaces.stream();
    }

    private SurfaceType[][] createSurfaceMap() {
        SurfaceType[][] surfaceMap = createEmptySurfaceMap();

        fillSurfaceMap(surfaceMap);
        return surfaceMap;
    }

    @SuppressWarnings("ExplicitArrayFilling")
    private SurfaceType[][] createEmptySurfaceMap() {
        int surfaceSize = random.randInt(properties.getMinSize(), properties.getMaxSize());
        log.debug("surfaceSize: {}", surfaceSize);
        SurfaceType[][] surfaceMap = new SurfaceType[surfaceSize][surfaceSize];
        for (int x = 0; x < surfaceMap.length; x++) {
            surfaceMap[x] = new SurfaceType[surfaceSize];
        }
        return surfaceMap;
    }

    private void fillSurfaceMap(SurfaceType[][] surfaceMap) {
        boolean initialPlacement = true;
        do {
            List<SurfaceType> surfaceTypeList = createSurfaceTypeList(initialPlacement);
            boolean ip = initialPlacement;
            surfaceTypeList.forEach(surfaceType -> fillBlockWithSurfaceType(surfaceMap, surfaceType, ip));
            initialPlacement = false;
        } while (hasNullFields(surfaceMap));
    }

    private boolean hasNullFields(SurfaceType[][] surfaceMap) {
        for (SurfaceType[] surfaceTypes : surfaceMap) {
            for (SurfaceType surfaceType : surfaceTypes) {
                if (isNull(surfaceType)) {
                    log.debug("There are empty fields.");
                    return true;
                }
            }
        }
        log.debug("No more empty fields");
        return false;
    }

    private List<SurfaceType> createSurfaceTypeList(boolean initialPlacement) {
        List<SurfaceType> result = properties.getSurfaceTypeSpawnDetails().stream()
            .flatMap(surfaceTypeSpawnDetails -> Stream.generate(surfaceTypeSpawnDetails::getSurfaceName).limit(surfaceTypeSpawnDetails.getSpawnRate()))
            .map(SurfaceType::valueOf)
            .collect(Collectors.toList());
        Collections.shuffle(result);

        if (initialPlacement) {
            log.debug("Initial placement. Filtering out optional surfaceTypes");
            List<SurfaceCreationProperties.SurfaceTypeSpawnDetails> optionalSpawnDetails = properties.getSurfaceTypeSpawnDetails().stream()
                .filter(SurfaceCreationProperties.SurfaceTypeSpawnDetails::isOptional)
                .collect(Collectors.toList());

            optionalSpawnDetails.stream()
                .filter((s) -> random.randBoolean())
                .peek(surfaceTypeSpawnDetails -> log.debug("SurfaceType {} will not spawn", surfaceTypeSpawnDetails.getSurfaceName()))
                .forEach(surfaceTypeSpawnDetails -> result.removeIf(surfaceType -> surfaceType.equals(SurfaceType.valueOf(surfaceTypeSpawnDetails.getSurfaceName()))));

        }
        log.debug("surfaceTypeList: {}", result);
        return result;
    }

    private void fillBlockWithSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType, boolean initialPlacement) {
        Optional<Coordinate> coordinateOptional = initialPlacement ? getRandomEmptySlot(surfaceMap) : getRandomEmptySlotNextToSurfaceType(surfaceMap, surfaceType);
        coordinateOptional.ifPresent(coordinate -> {
            surfaceMap[coordinate.getX()][coordinate.getY()] = surfaceType;
            log.debug("Coordinate {} filled with surfaceType {}. surfaceMap: {}", coordinate, surfaceType, surfaceMap);
        });
    }

    private Optional<Coordinate> getRandomEmptySlot(SurfaceType[][] surfaceMap) {
        Coordinate coordinate;
        do {
            coordinate = new Coordinate(random.randInt(0, surfaceMap.length - 1), random.randInt(0, surfaceMap.length - 1));
        } while (!isEmptySlot(surfaceMap, coordinate));
        log.debug("Random empty slot selected: {}", coordinate);
        return Optional.of(coordinate);
    }

    private boolean isEmptySlot(SurfaceType[][] surfaceMap, Coordinate coordinate) {
        return isNull(surfaceMap[coordinate.getX()][coordinate.getY()]);
    }

    private Optional<Coordinate> getRandomEmptySlotNextToSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType) {
        List<Coordinate> emptySlotsNextToSurfaceType = getEmptySlotsNextToSurfaceType(surfaceMap, surfaceType);

        if (emptySlotsNextToSurfaceType.isEmpty()) {
            log.debug("No more places for surfaceType {}", surfaceType);
            return Optional.empty();
        } else {
            Coordinate coordinate = emptySlotsNextToSurfaceType.get(random.randInt(0, emptySlotsNextToSurfaceType.size() - 1));
            log.debug("Random empty slot next to surfaceType {}: {}", surfaceType, coordinate);
            return Optional.of(coordinate);
        }
    }

    private List<Coordinate> getEmptySlotsNextToSurfaceType(SurfaceType[][] surfaceMap, SurfaceType surfaceType) {
        Set<Coordinate> slotsNextToSurfaceType = new HashSet<>();
        for (int x = 0; x < surfaceMap.length; x++) {
            SurfaceType[] surfaceTypes = surfaceMap[x];
            for (int y = 0; y < surfaceTypes.length; y++) {
                if (surfaceMap[x][y] == surfaceType) {
                    slotsNextToSurfaceType.add(new Coordinate(x - 1, y));
                    slotsNextToSurfaceType.add(new Coordinate(x + 1, y));
                    slotsNextToSurfaceType.add(new Coordinate(x, y - 1));
                    slotsNextToSurfaceType.add(new Coordinate(x, y + 1));
                }
            }
        }
        List<Coordinate> result = slotsNextToSurfaceType.stream()
            .filter(coordinate -> coordinate.getX() >= 0 && coordinate.getX() < surfaceMap.length)
            .filter(coordinate -> coordinate.getY() >= 0 && coordinate.getY() < surfaceMap.length)
            .filter(coordinate -> isEmptySlot(surfaceMap, coordinate))
            .collect(Collectors.toList());
        log.debug("Empty slots next to surfaceType {}: {}", surfaceType, slotsNextToSurfaceType);
        return result;
    }

    private List<Surface> mapSurfaces(SurfaceType[][] surfaceMap, Star star) {
        List<Surface> result = new ArrayList<>();
        for (int x = 0; x < surfaceMap.length; x++) {
            SurfaceType[] surfaceTypes = surfaceMap[x];
            for (int y = 0; y < surfaceTypes.length; y++) {
                Surface surface = surfaceFactory.create(
                    star.getStarId(),
                    star.getGameId(),
                    star.getOwnerId(),
                    new Coordinate(x, y),
                    surfaceMap[x][y]
                );
                result.add(surface);
            }
        }

        return result;
    }
}
