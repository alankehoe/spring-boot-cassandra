package com.alankehoe.bus;

import com.alankehoe.bus.subscribers.EventSubscriber;
import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Bus {
    
    private EventBus eventBus;

    @PostConstruct
    public void startEventBus() {
        eventBus = new EventBus();
        
        eventBus.register(new EventSubscriber());
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
