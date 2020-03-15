package ru.javamentor.onlineoffice.events;

import org.springframework.context.ApplicationEvent;

public class ActiveUserStoreUpdateEvent extends ApplicationEvent {
    private String message;

    public ActiveUserStoreUpdateEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}