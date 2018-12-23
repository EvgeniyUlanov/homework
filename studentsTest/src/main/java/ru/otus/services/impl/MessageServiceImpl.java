package ru.otus.services.impl;

import org.springframework.context.MessageSource;
import ru.otus.services.MessageService;

import java.util.Locale;

public class MessageServiceImpl implements MessageService {

    private MessageSource messageSource;
    private Locale locale;

    public MessageServiceImpl(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }
}
