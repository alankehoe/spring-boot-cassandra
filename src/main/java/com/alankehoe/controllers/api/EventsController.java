package com.alankehoe.controllers.api;

import com.alankehoe.events.EventManager;
import com.alankehoe.persistence.models.Event;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class EventsController extends BaseController {

    public static final String EVENTS_RESOURCE = "/events";

    @RequestMapping(value = EVENTS_RESOURCE, method = RequestMethod.GET, headers = REQUEST_HEADERS)
    public List<Event> index() {
        try {
            return getEventService("application").findAllAsync().get();
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = EVENTS_RESOURCE + "/{ref}", method = RequestMethod.GET, headers = REQUEST_HEADERS)
    public Event show(@PathVariable("ref") String ref) {
        try {
            return getEventService("application").findByRefAsync(UUID.fromString(ref)).get();
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = EVENTS_RESOURCE + "/create", method = RequestMethod.GET, headers = REQUEST_HEADERS)
    public Event create() {
        try {
            Event event = getEventService("application").createAsync(mockEvent()).get();
            EventManager.postAsync(event);
            return event;
        } catch (Exception e) {
            return null;
        }
    }

    private Event mockEvent() {
        Event event = new Event();
        event.setPayload("{payload: 'something'}");
        return event;
    }
}
