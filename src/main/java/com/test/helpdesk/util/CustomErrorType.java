package com.test.helpdesk.util;

import org.springframework.http.HttpStatus;

public class CustomErrorType {
    private String messages;

    public CustomErrorType(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
