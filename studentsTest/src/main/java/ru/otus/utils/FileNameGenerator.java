package ru.otus.utils;

public class FileNameGenerator {

    private final static String DEFAULT_LANGUAGE = "en";

    public static String generateFileName(String name, String locale, String suffix) {
        String fileName;
        if (!locale.equalsIgnoreCase(DEFAULT_LANGUAGE)) {
            fileName = name + "_" + locale + suffix;
        } else {
            fileName = name + suffix;
        }
        return fileName;
    }
}
