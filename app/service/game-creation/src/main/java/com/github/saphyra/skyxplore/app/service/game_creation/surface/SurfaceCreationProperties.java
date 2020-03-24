package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @AllArgsConstructor
    @NoArgsConstructor
    static class SurfaceTypeSpawnDetails {
        @NotNull
        private String surfaceName;

        @NotNull
        private Integer spawnRate;

        private boolean optional = false;
    }
}
