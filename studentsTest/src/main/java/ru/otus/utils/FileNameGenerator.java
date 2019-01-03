package ru.otus.utils;

public class FileNameGenerator {

    private final static String DEFAULT_LANGUAGE = "en";
    private String name;
    private String suffix;
    private String locale;

    public FileNameGenerator(String name, String locale, String suffix) {
        this.name = name;
        this.locale = locale;
        this.suffix = suffix;
    }

    public String generateFileName() {
        String fileName;
        if (!locale.equalsIgnoreCase(DEFAULT_LANGUAGE)) {
            fileName = name + "_" + locale + suffix;
        } else {
            fileName = name + suffix;
        }
        return fileName;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
