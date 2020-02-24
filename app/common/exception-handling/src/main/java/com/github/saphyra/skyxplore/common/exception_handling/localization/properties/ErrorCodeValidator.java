package com.github.saphyra.skyxplore.common.exception_handling.localization.properties;

import com.github.saphyra.skyxplore.common.data.DataValidator;
import com.github.saphyra.skyxplore.common.exception_handling.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@Slf4j
public class ErrorCodeValidator implements DataValidator<Map<String, ErrorCodeLocalization>> {

    @Override
    public void validate(Map<String, ErrorCodeLocalization> item) {
        item.forEach(this::validate);
    }

    private void validate(String locale, ErrorCodeLocalization errorCodeLocalization) {
        try {
            if (isEmpty(errorCodeLocalization)) {
                throw new IllegalStateException("ErrorCodeLocalization must not be empty.");
            }

            Arrays.stream(ErrorCode.values())
                .forEach(errorCode -> validate(errorCode, errorCodeLocalization));

            errorCodeLocalization.keySet()
                .forEach(this::validate);
        } catch (Exception e) {
            throw new IllegalStateException("ErrorCodaLocalization validation failed for locale " + locale, e);
        }
    }

    private void validate(String errorCode) {
        if (!ErrorCode.fromValue(errorCode).isPresent()) {
            log.warn("{} is not present in enum ErrorCode. Can be removed from localization.", errorCode);
            //TODO restore validation
            //throw new IllegalStateException(errorCode + " is not present in enum ErrorCode. Can be removed from localization.");
        }
    }

    private void validate(ErrorCode errorCode, ErrorCodeLocalization errorCodeLocalization) {
        Optional<String> errorCodeOptional = errorCodeLocalization.getOptional(errorCode.name());
        if (!errorCodeOptional.isPresent()) {
            throw new IllegalStateException("Localization missing for ErrorCode " + errorCode);
        }

        if (isEmpty(errorCodeOptional.get())) {
            throw new IllegalStateException("Localization must not be null for empty. ErrorCode: " + errorCode);
        }
    }
}
