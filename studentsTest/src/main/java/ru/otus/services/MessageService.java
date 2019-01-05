package ru.otus.services;

import java.util.Locale;

public interface MessageService {

    String getMessage(String code);

    void setLocale(Locale locale);
}
