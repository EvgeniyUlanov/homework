package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.services.MessageService;
import ru.otus.services.impl.MessageServiceImpl;
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
    public FileNameGenerator fileNameGenerator(ApplicationProperties appProp) {
        return new FileNameGenerator(appProp.getName(), appProp.getLocale(), appProp.getSuffix());
    }
}