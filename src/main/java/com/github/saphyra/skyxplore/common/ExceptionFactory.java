package com.github.saphyra.skyxplore.common;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.PreconditionFailedException;
import com.github.saphyra.exceptionhandling.exception.RestException;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ExceptionFactory {
    private static final String BUILDING_NOT_FOUND_PREFIX = "Building not found with buildingId %s and playerId %s";
    private static final String CITIZEN_NOT_FOUND_PREFIX = "Citizen not found with citizenId %s and playerId %s";
    private static final String CONSTRUCTION_IN_PROGRESS_PREFIX = "Construction already in progress for surface %s";
    private static final String CONSTRUCTION_NOT_FOUND_PREFIX = "Construction not found with constructionId %s, gameId %s and playerId %s";
    private static final String DATA_NOT_FOUND_PREFIX = "Data not found with dataId %s";
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found with gameId %s and userId %s";
    private static final String INVALID_BUILD_LOCATION_PREFIX = "%s cannot be built at surfaceId %s";
    private static final String INVALID_CITIZEN_NAME_PREFIX = "Invalid citizen name: %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported";
    private static final String MAX_LEVEL_REACHED_PREFIX = "Max level reached for building %s";
    private static final String PLAYER_NOT_FOUND_PREFIX = "Player not found with gameId %s and userId %s";
    private static final String RESEARCH_NOT_PRESENT_PREFIX = "Research not present.";
    private static final String STAR_NOT_FOUND_PREFIX = "Star not found with starId %s";
    private static final String STORAGE_FULL_PREFIX = "Storage for type %s is full at system %s";
    private static final String SURFACE_NOT_FOUND_PREFIX = "Surface not found with id %s";
    private static final String TERRAFORMING_NOT_POSSIBLE_PREFIX = "Surface %s cannot be terraformed to %s";
    private static final String UPGRADE_ALREADY_IN_PROGRESS_PREFIX = "Upgrade already in progress for building %s";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with userId %s";
    private static final String TERRAFORMING_ALREADY_IN_PROGRESS_PREFIX = "Terraforming of surface %s is already in progress";

    public static RestException buildingNotFound(UUID buildingId, UUID playerId) {
        return new NotFoundException(createErrorMessage(ErrorCode.BUILDING_NOT_FOUND), String.format(BUILDING_NOT_FOUND_PREFIX, buildingId, playerId));
    }

    public static RestException citizenNotFound(UUID citizenId, UUID playerId) {
        return new NotFoundException(createErrorMessage(ErrorCode.CITIZEN_NOT_FOUND), String.format(CITIZEN_NOT_FOUND_PREFIX, citizenId, playerId));
    }

    public static RestException constructionInProgress(UUID surfaceId) {
        return new ConflictException(createErrorMessage(ErrorCode.CONSTRUCTION_IN_PROGRESS), String.format(CONSTRUCTION_IN_PROGRESS_PREFIX, surfaceId));
    }

    public static RestException constructionNotFound(UUID constructionId, UUID gameId, UUID playerId) {
        return new NotFoundException(createErrorMessage(ErrorCode.CONSTRUCTION_NOT_FOUND), String.format(CONSTRUCTION_NOT_FOUND_PREFIX, constructionId, gameId, playerId));
    }

    public static RestException dataNotFound(String dataId) {
        return new NotFoundException(createErrorMessage(ErrorCode.DATA_NOT_FOUND), String.format(DATA_NOT_FOUND_PREFIX, dataId));
    }

    public static RestException gameNotFound(UUID gameId, UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, gameId, userId));
    }

    public static RestException invalidBuildLocation(String dataId, UUID surfaceId) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_BUILD_LOCATION), String.format(INVALID_BUILD_LOCATION_PREFIX, dataId, surfaceId));
    }

    public static RestException invalidCitizenName(String newName) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_CITIZEN_NAME), String.format(INVALID_CITIZEN_NAME_PREFIX, newName));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException maxLevelReached(UUID buildingId) {
        return new BadRequestException(createErrorMessage(ErrorCode.MAX_LEVEL_REACHED), String.format(MAX_LEVEL_REACHED_PREFIX, buildingId));
    }

    public static RestException playerNotFound(UUID userId, UUID gameId) {
        return new NotFoundException(createErrorMessage(ErrorCode.PLAYER_NOT_FOUND), String.format(PLAYER_NOT_FOUND_PREFIX, gameId, userId));
    }

    public static RestException researchNotPresentException() {
        return new PreconditionFailedException(createErrorMessage(ErrorCode.RESEARCH_NOT_PRESENT), RESEARCH_NOT_PRESENT_PREFIX);
    }

    public static RestException starNotFound(UUID starId) {
        return new NotFoundException(createErrorMessage(ErrorCode.STAR_NOT_FOUND), String.format(STAR_NOT_FOUND_PREFIX, starId.toString()));
    }

    public static RestException storageFull(UUID surfaceId, StorageType storageType) {
        return new BadRequestException(createErrorMessage(ErrorCode.STORAGE_FULL), String.format(STORAGE_FULL_PREFIX, storageType, surfaceId));
    }

    public static NotFoundException surfaceNotFound(UUID surfaceId) {
        return new NotFoundException(createErrorMessage(ErrorCode.SURFACE_NOT_FOUND), String.format(SURFACE_NOT_FOUND_PREFIX, surfaceId));
    }

    public static RestException terraformingAlreadyInProgress(UUID surfaceId) {
        return new ConflictException(createErrorMessage(ErrorCode.TERRAFORMING_ALREADY_IN_PROGRESS), String.format(TERRAFORMING_ALREADY_IN_PROGRESS_PREFIX, surfaceId));
    }

    public static RestException terraformingNotPossible(UUID surfaceId, SurfaceType surfaceType) {
        return new BadRequestException(createErrorMessage(ErrorCode.TERRAFORMING_NOT_POSSIBLE), String.format(TERRAFORMING_NOT_POSSIBLE_PREFIX, surfaceId, surfaceType));
    }

    public static RestException upgradeAlreadyInProgress(UUID buildingId) {
        return new ConflictException(createErrorMessage(ErrorCode.UPGRADE_ALREADY_IN_PROGRESS), String.format(UPGRADE_ALREADY_IN_PROGRESS_PREFIX, buildingId));
    }

    public static RestException userNameAlreadyExists(String userName) {
        return new ConflictException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    public static RestException userNotFound(UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.USER_NOT_FOUND), String.format(USER_NOT_FOUND_PREFIX, userId.toString()));
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }
}
