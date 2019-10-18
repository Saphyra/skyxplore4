package com.github.saphyra.skyxplore.game.map.surface.creation;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.StarsCreatedEvent;
import com.github.saphyra.skyxplore.game.common.coordinates.domain.Coordinate;
import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.map.surface.domain.Surface;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceDao;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurfaceCreationService {
    private final IdGenerator idGenerator;
    private final Random random;
    private final SurfaceCreationProperties properties;
    private final SurfaceDao surfaceDao;

    @EventListener
    void starCreatedEventListener(StarsCreatedEvent starsCreatedEvent) {
        log.info("Creating surfaces...");
        List<Surface> surfaces = starsCreatedEvent.getStars().stream()
            .parallel()
            .flatMap(this::createSurfaces)
            .collect(Collectors.toList());
        log.info("Number of surfaces created: {}", surfaces.size());
        surfaceDao.saveAll(surfaces);
    }

    private Stream<Surface> createSurfaces(Star star) {
        log.debug("Creating surfaces for star {}", star.getStarId());
        SurfaceType[][] surfaceMap = createSurfaceMap();
        log.debug("Surfaces created for star {}", star.getStarId());
        return mapSurfaces(surfaceMap, star).stream();
    }

    private SurfaceType[][] createSurfaceMap() {
        SurfaceType[][] surfaceMap = createEmptySurfaceMap();

        boolean shouldPlaceVulcan = random.randBoolean();
        log.debug("shouldPlaceVulcan: {}", shouldPlaceVulcan);
        fillSurfaceMap(surfaceMap, shouldPlaceVulcan);
        return surfaceMap;
    }

    private SurfaceType[][] createEmptySurfaceMap() {
        int surfaceSize = random.randInt(properties.getMinSize(), properties.getMaxSize());
        log.debug("surfaceSize: {}", surfaceSize);
        SurfaceType[][] surfaceMap = new SurfaceType[surfaceSize][surfaceSize];
        for (int x = 0; x < surfaceMap.length; x++) {
            surfaceMap[x] = new SurfaceType[surfaceSize];
        }
        return surfaceMap;
    }

    private void fillSurfaceMap(SurfaceType[][] surfaceMap, boolean shouldPlaceVulcan) {
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
                Surface surface = Surface.builder()
                    .surfaceId(idGenerator.randomUUID())
                    .starId(star.getStarId())
                    .userId(star.getUserId())
                    .gameId(star.getGameId())
                    .coordinate(new Coordinate(x, y))
                    .surfaceType(surfaceMap[x][y])
                    .build();
                result.add(surface);
            }
        }

        return result;
    }
}