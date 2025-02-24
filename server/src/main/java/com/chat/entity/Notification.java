package com.chat.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable {
    private int Id;
    private String desc;
    private Timestamp time;
    private int senderId;
    private boolean isMessage;
    private int chat_id;
    private status stat=status.UNREAD;
    public Notification() {

    }

    public Notification(int Id, String desc, Timestamp time, int senderId, boolean isMessage, int chat_id) {
        this.time = time;
        this.Id = Id;
        this.desc = desc;
        this.isMessage = isMessage;
        this.senderId = senderId;
        this.chat_id = chat_id;

    }

    public Notification(String desc, Timestamp time, int senderId, boolean isMessage, int chat_id) {
        this.time = time;
        this.desc = desc;
        this.isMessage = isMessage;
        this.senderId = senderId;
        this.chat_id = chat_id;

    }
    public Notification(int Id,String desc, Timestamp time, int senderId, boolean isMessage, int chat_id,status stat) {
        this.time = time;
        this.desc = desc;
        this.Id = Id;
        this.isMessage = isMessage;
        this.senderId = senderId;
        this.chat_id = chat_id;
        this.stat=stat;

    }

    public status getStat() {
        return stat;
    }

    public void setStat(status stat) {
        this.stat = stat;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return Id;
    }

    public int getSenderId() {
        return senderId;
    }

    public Timestamp getTime() {
        return time;
    }

    public boolean getIsMessage() {
        return isMessage;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setMessage(boolean isMessage) {
        this.isMessage = isMessage;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Notification [ID=" + Id + ", desc : " + desc + ", time" + time + "sender_id "+senderId+"  status"+stat+ "]";
    }

    public enum status {
        READ, UNREAD

    }
}