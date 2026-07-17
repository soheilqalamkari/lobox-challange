package com.lobox.challenge.lobxchallenge.utils.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMessageResolver extends MessageSourceAccessor {

    private final MessageSourceAccessor messageSourceAccessor;

    public ExceptionMessageResolver(MessageSource messageSource) {
        super(messageSource);
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return messageSourceAccessor.getMessage(code);
    }
}
