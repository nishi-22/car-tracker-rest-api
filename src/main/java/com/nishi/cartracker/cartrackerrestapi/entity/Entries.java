package com.nishi.cartracker.cartrackerrestapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "entries")
public class Entries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="entryID")
    private Integer entryId;

    @Column(name="guestName")
    private String guestName;

    @Column(name="content")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
