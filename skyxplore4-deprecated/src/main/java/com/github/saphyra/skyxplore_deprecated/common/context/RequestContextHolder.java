package com.github.saphyra.skyxplore_deprecated.common.context;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestContextHolder {
    private final ThreadLocal<RequestContext> storage = new ThreadLocal<>();

    public void setContext(RequestContext context) {
        storage.set(context);
    }

    public RequestContext get() {
        return Optional.ofNullable(storage.get())
            .orElseThrow(() -> new IllegalStateException("RequestContext is not set for the current thread."));
    }

    public void clear() {
        storage.remove();
    }
}
