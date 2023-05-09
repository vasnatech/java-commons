package com.vasnatech.commons.serialize;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class MediaTypes {

    private MediaTypes() {}

    static Map<String, MediaType> groupedByName = new HashMap<>();
    static Map<String, MediaType> groupedByFileExtension = new HashMap<>();
    static Map<String, MediaType> groupedByMimeType = new HashMap<>();

    public static void add(MediaType mediaType) {
        groupedByName.put(mediaType.name(), mediaType);
        mediaType.fileExtensions().forEach(fileExtension -> groupedByFileExtension.put(fileExtension.toLowerCase(Locale.ENGLISH), mediaType));
        groupedByMimeType.put(mediaType.mimeType().toLowerCase(Locale.ENGLISH), mediaType);
    }

    public MediaType getByName(String name) {
        return groupedByName.get(name);
    }

    public MediaType getByFileExtension(String fileExtension) {
        return groupedByFileExtension.get(fileExtension.toLowerCase(Locale.ENGLISH));
    }

    public MediaType getByMimeType(String mimeType) {
        return groupedByMimeType.get(mimeType.toLowerCase(Locale.ENGLISH));
    }
}
