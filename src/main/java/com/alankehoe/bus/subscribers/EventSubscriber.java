package com.alankehoe.bus.subscribers;

import com.alankehoe.persistence.models.Event;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSubscriber {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventSubscriber.class);
    
    @Subscribe
    public void handleEvent(Event event){
        LOGGER.info("Cool stuff {}", event.getPayload());
    }
}
