package pro.sky.telegrambot.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = " notification_task")
public class NotificationTask {
    private Object GenerationType;
    @Id
    @GeneratedValue(strategy = GenerationType.INDENTITY)
    private long id;
    private String text;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "chat_id")
    private LocalDateTime dateTime;

    public NotificationTask(){
    }

    public NotificationTask(String text, long chatId, LocalDateTime dateTime){
        this.text = text;
        this.chatId = chatId;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", chatId=" + chatId +
                ", dateTime=" + dateTime +
                '}';
    }
    @java.lang.Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        NotificationTask that = (NotificationTask) object;
        return id == that.id;
    }
    @java.lang.Override
    public int hashCode() {
        return Objects.hash(id);
    }
}