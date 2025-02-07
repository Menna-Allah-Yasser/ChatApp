package com.chat.utils;

import java.sql.Timestamp;

public class Card {
    private int id;
    private int senderID;
    private String desc;
    private Byte[] img;
    private String senderName;
    private Timestamp time;
    private CardType cardType;
    public Card(int id,int senderID,String desc,Byte[] img,String senderName ,Timestamp time,CardType cardType)
    {
        this.id=id;
        this.senderID=senderID;
        this.desc=desc;
        this.img=img;
        this.time=time;
        this.senderName=senderName;
        this.cardType=cardType;
    }
    public Card(CardType cardType)
    {
        this.cardType=cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImg(Byte[] img) {
        this.img = img;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Byte[] getImg() {
        return img;
    }

    public int getId() {
        return id;
    }

    public int getSenderID() {
        return senderID;
    }

    public String getDesc() {
        return desc;
    }

    public String getSenderName() {
        return senderName;
    }

    public Timestamp getTime() {
        return time;
    }


    public enum CardType
    {
        NOTIFICATION,MESSAGE,FRIENDREQUEST,CHAT,USER,ANNOUNCEMENT
    }

}
