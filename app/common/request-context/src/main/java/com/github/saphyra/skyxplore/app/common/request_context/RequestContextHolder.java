package com.github.saphyra.skyxplore.app.common.request_context;

import java.util.Optional;

import org.springframework.stereotype.Component;

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
