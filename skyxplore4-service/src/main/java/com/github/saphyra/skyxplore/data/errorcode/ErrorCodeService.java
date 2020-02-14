package com.github.saphyra.skyxplore.data.errorcode;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.ValidationAbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.ContentLoaderFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ErrorCodeService extends ValidationAbstractDataService<String, ErrorCodeLocalization> {
    public ErrorCodeService(ContentLoaderFactory contentLoaderFactory, ErrorCodeValidator errorCodeValidator) {
        super("public/i18n/error_code", contentLoaderFactory, errorCodeValidator);
    }

    @Override
    @PostConstruct
    public void init() {
        super.load(ErrorCodeLocalization.class);
    }

    @Override
    public void addItem(ErrorCodeLocalization content, String fileName) {
        put(FilenameUtils.removeExtension(fileName), content);
    }

    public void validateLocale(String locale) {
        if (!isValidLocale(locale)) {
            throw ExceptionFactory.invalidLocale(locale);
        }
    }

    private boolean isValidLocale(String locale) {
        return containsKey(locale);
    }
}
