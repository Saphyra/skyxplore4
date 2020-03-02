package com.github.saphyra.skyxplore.test.frontend.main_menu.game_crud;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GameNameValidationResult {
    GAME_NAME_TOO_SHORT("Játéknév túl rövid (minimum 3 karakter)."),
    GAME_NAME_TOO_LONG("Játéknév túl hosszú (maximum 30 karakter).")
    ;

    private final String errorMessage;
}
