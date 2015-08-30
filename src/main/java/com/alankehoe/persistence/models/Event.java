package com.alankehoe.persistence.models;

public class Event extends Entity {
    
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
