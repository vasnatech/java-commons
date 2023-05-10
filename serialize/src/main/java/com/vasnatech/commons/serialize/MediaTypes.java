package com.vasnatech.commons.serialize;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class MediaTypes {

    private MediaTypes() {}

    static Map<String, MediaType> GROUPED_BY_NAME = new HashMap<>();
    static Map<String, MediaType> GROUPED_BY_FILE_EXTENSION = new HashMap<>();
    static Map<String, MediaType> GROUPED_BY_MIME_TYPE = new HashMap<>();

    public static void add(MediaType mediaType) {
        GROUPED_BY_NAME.put(mediaType.name(), mediaType);
        mediaType.fileExtensions().forEach(fileExtension -> GROUPED_BY_FILE_EXTENSION.put(fileExtension.toLowerCase(Locale.ENGLISH), mediaType));
        GROUPED_BY_MIME_TYPE.put(mediaType.mimeType().toLowerCase(Locale.ENGLISH), mediaType);
    }

    public static MediaType getByName(String name) {
        return GROUPED_BY_NAME.get(name);
    }

    public static MediaType getByFileExtension(String fileExtension) {
        return GROUPED_BY_FILE_EXTENSION.get(fileExtension.toLowerCase(Locale.ENGLISH));
    }

    public static MediaType getByMimeType(String mimeType) {
        return GROUPED_BY_MIME_TYPE.get(mimeType.toLowerCase(Locale.ENGLISH));
    }
}
