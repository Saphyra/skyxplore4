package com.github.saphyra.skyxplore.common.utils;

import com.github.saphyra.converter.ConverterBase;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidConverter extends ConverterBase<String, UUID> {
    @Override
    protected String processDomainConversion(UUID uuid) {
        return uuid.toString();
    }

    @Override
    protected UUID processEntityConversion(String s) {
        return UUID.fromString(s);
    }
}
