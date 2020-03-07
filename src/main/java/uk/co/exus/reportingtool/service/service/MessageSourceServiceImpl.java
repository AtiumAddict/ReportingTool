package uk.co.exus.reportingtool.service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class MessageSourceServiceImpl implements MessageSourceService {

    @Autowired
    private MessageSource messageSource;

    public String interpolate(String key) {
        try {
            return messageSource.getMessage(key, null, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            log.warn("key not found", ex);
            return key;
        }
    }
}
