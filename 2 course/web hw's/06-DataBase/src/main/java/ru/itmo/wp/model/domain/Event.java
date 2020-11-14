package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Event extends Thing implements Serializable {
    private long userId;
    private EventType type;

    public enum EventType {
        ENTER, LOGOUT;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
