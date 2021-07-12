package com.example.messagingstompwebsocket.messages;

import java.util.List;

public class SetCellsMessage {
    private long id;
    private String sender;
    private List<List<Object>> changes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<List<Object>> getChanges() {
        return changes;
    }

    public void setChanges(List<List<Object>> changes) {
        this.changes = changes;
    }
}
