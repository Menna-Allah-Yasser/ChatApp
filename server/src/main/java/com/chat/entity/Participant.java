package com.chat.entity;

import java.io.Serializable;

public class Participant implements Serializable {
    private int chatId;
    private int particpantId;
    private State state;
    private Category category;

    public Participant(int chatId, int particpantId, State state, Category category) {
        this.chatId = chatId;
        this.particpantId = particpantId;
        this.state = state;
        this.category = category;
    }

    public Participant() {
    }

    public State getState() {
        return state;
    }

    public Category getCategory() {
        return category;
    }

    public int getChatId() {
        return chatId;
    }

    public int getParticpantId() {
        return particpantId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public enum Category {
        WORK, FAMILY, FRIEND;
    }

    public enum State {
        BLOCKED, AVAILABLE;
    }

}
