package ru.otus.services.impl;

import org.junit.Test;
import org.springframework.context.MessageSource;
import ru.otus.services.MessageService;

import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageServiceImplTest {

    @Test
    public void testGetMessage() {
        MessageSource messageSource = mock(MessageSource.class);
        Locale locale = new Locale("en");
        when(messageSource.getMessage("test", null, locale)).thenReturn("test string");
        MessageService messageService = new MessageServiceImpl(messageSource, locale);
        String output = messageService.getMessage("test");
        assertThat(output, is("test string"));
    }
}
