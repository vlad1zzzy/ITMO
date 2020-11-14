package ru.itmo.wp.model.domain;

import java.io.Serializable;

public class Talk extends Thing implements Serializable {
    private long sourceUserId;
    private long targetUserId;
    private String text;

    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long id) {
        this.sourceUserId = id;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long id) {
        this.targetUserId = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
