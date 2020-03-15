package ru.javamentor.onlineoffice.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ActiveUserStoreUpdateEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishUpdateEvent(final String message) {
        ActiveUserStoreUpdateEvent activeUserStoreUpdateEvent = new ActiveUserStoreUpdateEvent(this, message);
        applicationEventPublisher.publishEvent(activeUserStoreUpdateEvent);
    }
}