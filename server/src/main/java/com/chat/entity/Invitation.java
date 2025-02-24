package com.chat.entity;


import java.io.Serializable;

public class Invitation implements Serializable {
    private int senderId;
    private int receiverId;
    private InvStatus status;

    public Invitation() {}

    public Invitation(int senderId, int receiverId, InvStatus status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public Invitation(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = InvStatus.WAIT;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public InvStatus getStatus() {
        return status;
    }

    public void setStatus(InvStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", status=" + status +
                '}';
    }
}
