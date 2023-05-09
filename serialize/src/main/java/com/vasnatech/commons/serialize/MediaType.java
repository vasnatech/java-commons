package com.vasnatech.commons.serialize;

import java.util.Set;

public interface MediaType {

    String name();
    String mimeType();
    Set<String> fileExtensions();

    static MediaType of(String name, String mimeType, String... fileExtensions) {
        return of(name, mimeType, Set.of(fileExtensions));
    }

    static MediaType of(String name, String mimeType, Set<String> fileExtensions) {
        return new DefaultMediaType(name, mimeType, fileExtensions);
    }

    record DefaultMediaType(String name, String mimeType, Set<String> fileExtensions) implements MediaType {}
}
