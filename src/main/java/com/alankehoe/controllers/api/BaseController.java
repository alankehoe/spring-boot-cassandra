package com.alankehoe.controllers.api;

import com.alankehoe.controllers.ApplicationController;
import com.alankehoe.persistence.client.AstyanaxClient;
import com.alankehoe.persistence.services.EventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseController extends ApplicationController {

    public static final String REQUEST_HEADERS = "Accept=application/json";
    
    protected EventService getEventService(String keyspaceName) {
        return new EventService(keyspaceName);
    }
}
