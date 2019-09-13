package com.github.saphyra.skyxplore.data.errorcode;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.base.loader.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ErrorCodeService extends AbstractDataService<String, ErrorCodeLocalization> {
    public ErrorCodeService(FileUtil fileUtil) {
        super("public/i18n/error_code", fileUtil);
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
