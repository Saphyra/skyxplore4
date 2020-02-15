package com.github.saphyra.skyxplore_deprecated.game.service.map.surface.creation;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "com.github.saphyra.skyxplore.game.surface")
@Validated
@Slf4j
class SurfaceCreationProperties {
    @NotNull
    private Integer minSize;

    @NotNull
    private Integer maxSize;

    @NotNull
    private List<SurfaceTypeSpawnDetails> surfaceTypeSpawnDetails;

    @Validated
    @Data
    static class SurfaceTypeSpawnDetails {
        @NotNull
        private String surfaceName;

        @NotNull
        private Integer spawnRate;

        private boolean optional = false;
    }
}
