package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.impl.QuestionDaoFromFile;
import ru.otus.services.MessageService;
import ru.otus.services.QuestionService;
import ru.otus.services.impl.MessageServiceImpl;
import ru.otus.services.impl.QuestionServiceImpl;
import ru.otus.utils.FileNameGenerator;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public MessageService messageService(ApplicationProperties appProp) {
        Locale locale = new Locale(appProp.getLocale());
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:bundle");
        messageSource.setDefaultEncoding("utf-8");
        return new MessageServiceImpl(messageSource, locale);
    }

    @Bean
    public QuestionService questionService(ApplicationProperties appProp) {
        String file = FileNameGenerator
                .generateFileName(appProp.getName(), appProp.getLocale(), appProp.getSuffix());
        return new QuestionServiceImpl(new QuestionDaoFromFile(file));
    }
}