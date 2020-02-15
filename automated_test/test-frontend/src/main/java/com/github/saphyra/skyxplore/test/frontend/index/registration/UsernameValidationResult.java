package com.github.saphyra.skyxplore.test.frontend.index.registration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UsernameValidationResult {
    VALID(null),
    TOO_SHORT("Felhasználónév túl rövid (Minimum 3 karakter)."),
    TOO_LONG("Felhasználónév túl hosszú (Maximum 30 karakter).");

    private final String errorMessage;
}
