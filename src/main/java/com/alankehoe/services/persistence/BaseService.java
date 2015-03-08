package com.alankehoe.services.persistence;

import com.alankehoe.initializers.AstyanaxClient;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected AstyanaxClient astyanaxClient;
}
