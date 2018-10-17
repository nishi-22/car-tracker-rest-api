package com.nishi.cartracker.cartrackerrestapi.entity;

import javax.persistence.Entity;

public class GuestUser {

    private String guestName;
    private String content;
    private Integer entryId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
}