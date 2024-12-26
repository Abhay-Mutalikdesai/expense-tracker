package com.example.expense_tracker.utility;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class MessageUtil {
    private final MessageSource messageSource;
    private final WebRequest webRequest;

    public MessageUtil(MessageSource messageSource, WebRequest webRequest) {
        this.messageSource = messageSource;
        this.webRequest = webRequest;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, webRequest.getLocale());
    }

    public String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, webRequest.getLocale());
    }
}

