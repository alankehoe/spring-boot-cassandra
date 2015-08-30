package com.alankehoe.controllers.api;

import com.alankehoe.bus.Bus;
import com.alankehoe.controllers.ApplicationController;
import com.alankehoe.initializers.AstyanaxClient;
import com.alankehoe.persistence.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaseController extends ApplicationController {

    @Autowired
    protected AstyanaxClient astyanaxClient;
    
    @Autowired
    protected Bus bus;

    protected EventService getEventService(String keyspaceName) {
        return new EventService(astyanaxClient.getCluster(), keyspaceName);
    }
}
