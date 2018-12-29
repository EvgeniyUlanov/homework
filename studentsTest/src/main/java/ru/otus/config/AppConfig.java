package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.impl.QuestionDaoFromFile;
import ru.otus.services.MessageService;
import ru.otus.services.QuestionService;
import ru.otus.services.impl.MessageServiceImpl;
import ru.otus.services.impl.QuestionServiceImpl;

import java.util.Locale;

@Configuration
public class AppConfig {

    private final static String DEFAULT_LANGUAGE = "en";

    @Bean
    public MessageService messageService(ApplicationProperties appProp) {
        Locale locale = new Locale(appProp.getLocale());
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:bundle");
        messageSource.setDefaultEncoding("utf-8");
        return new MessageServiceImpl(messageSource, locale);
    }

    @Bean
    public QuestionService questionService(ApplicationProperties appProp, MessageService messageService) {
        String file;
        if (!appProp.getLocale().equalsIgnoreCase(DEFAULT_LANGUAGE)) {
            file = appProp.getName() + "_" + appProp.getLocale() + appProp.getSuffix();
        } else {
            file = appProp.getName() + appProp.getSuffix();
        }
        String testName = messageService.getMessage("test.name");
        return new QuestionServiceImpl(new QuestionDaoFromFile(testName, file));
    }
}