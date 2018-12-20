package ru.otus.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoFromFile;

import java.util.Locale;

@Configuration
public class AppConfig {

    private final static String DEFAULT_LANGUAGE = "en";

    @Bean("messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:bundle");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }

    @Bean
    public Locale getLocale(ApplicationProperties appProp) {
        return new Locale(appProp.getLocale());
    }

    @Bean
    public QuestionDao getQuestionDao(ApplicationProperties appProp) {
        String file;
        if (!appProp.getLocale().equalsIgnoreCase(DEFAULT_LANGUAGE)) {
            file = appProp.getName() + "_" + appProp.getLocale() + appProp.getSuffix();
        } else {
            file = appProp.getName() + appProp.getSuffix();
        }
        return new QuestionDaoFromFile(file);
    }
}