package com.alankehoe.events;

import com.alankehoe.events.subscribers.EventSubscriber;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

public class EventManager {

    private static final int THREAD_POOL_SIZE = 10;

    private static EventBus asyncEventBus;
    private static EventBus eventBus;

    static {
        ListeningExecutorService executorService = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(THREAD_POOL_SIZE));

        eventBus = new EventBus();
        asyncEventBus = new AsyncEventBus(executorService);
        
        registerSubscribers(eventBus, asyncEventBus);
    }

    public static void post(Object object) {
        eventBus.post(object);
    }
    
    public static void postAsync(Object object) {
        asyncEventBus.post(object);
    }
    
    private static void registerSubscribers(EventBus... buses) {
        for (EventBus eventBus : buses) {
            eventBus.register(new EventSubscriber());
        }
    }
}
