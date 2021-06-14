package com.edu.hutech.models;

import lombok.Data;

@Data
public class UploadEvent {

    private String eventType = "progress";

    private Object state;

    public UploadEvent() {}

    public UploadEvent(String eventType, Object state) {
        this.eventType = eventType;
        this.state = state;
    }

}
