package com.chat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message  implements Serializable {

    private int id;

    private String description;

    private LocalDateTime time;

    private String file_url;
    private String file_type;

    private final int chat_id;

    private final int user_id;

    public Message(
           String description, LocalDateTime time, int chat_id, int user_id) {
        this.description = description;
        this.time = time;
        this.chat_id = chat_id;
        this.user_id = user_id;
    }

    public Message(
            String description, LocalDateTime time, String file_url, String file_type, int chat_id, int user_id) {
        this.description = description;
        this.time = time;
        this.file_url = file_url;
        this.file_type = file_type;
        this.chat_id = chat_id;
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public int getChat_id() {
        return chat_id;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", file_url='" + file_url + '\'' +
                ", file_type='" + file_type + '\'' +
                ", chat_id=" + chat_id +
                ", user_id=" + user_id +
                '}';
    }
}
