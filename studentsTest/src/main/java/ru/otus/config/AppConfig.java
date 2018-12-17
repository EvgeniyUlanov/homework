package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.impl.QuestionDaoFromFile;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean("messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:bundle");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }

    @Bean
    public Locale getLocale(@Value("${locale}") String locale) {
        return new Locale(locale);
    }

    @Bean
    public QuestionDao getQuestionDao(@Value("${locale}") String language,
                                      @Value("${question.file.name}") String fileName,
                                      @Value("${question.file.suffix}") String fileSuffix) {
        String file;
        if (!language.equalsIgnoreCase("en")) {
            file = fileName + "_" + language + fileSuffix;
        } else {
            file = fileName + fileSuffix;
        }
        return new QuestionDaoFromFile(file);
    }
}
