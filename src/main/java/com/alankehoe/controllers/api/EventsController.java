package com.alankehoe.controllers.api;

import com.alankehoe.events.EventManager;
import com.alankehoe.persistence.models.Event;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class EventsController extends BaseController {
    
    @RequestMapping(value = "/events", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Event> index() {
        try {
            return getEventService("application").findAll();
        } catch (ConnectionException e) {
            return null;
        }
    }

    @RequestMapping(value = "/events/{ref}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Event show(@PathVariable("ref") String ref) {
        try {
            return getEventService("application").findByRef(UUID.fromString(ref));
        } catch (ConnectionException e) {
            return null;
        }
    }

    @RequestMapping(value = "/events/create", method = RequestMethod.GET, headers = "Accept=application/json")
    public Event create() {
        try {
            Event event = getEventService("application").create(mockEvent());
            EventManager.postAsync(event);
            return event;
        } catch (ConnectionException e) {
            return null;
        }
    }

    private Event mockEvent() {
        Event event = new Event();
        event.setPayload("{payload: 'something'}");
        return event;
    }
}
