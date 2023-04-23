package com.example.application.entities;

import com.example.application.enums.Status;

import javax.persistence.*;

@Entity
@Table(name = "messages", schema = "public", catalog = "ZeusDB")
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "chat_id")
    private Long chatId;
    @Basic
    @Column(name = "message")
    private String message;
    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @Basic
    @Column(name = "title")
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        if (id != that.id) return false;
        if (chatId != null ? !chatId.equals(that.chatId) : that.chatId != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (chatId != null ? chatId.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
