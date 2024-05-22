package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Column(name = "chatId")
    private Long chatId;

    @Getter
    @Size(max = 255)
    @Column(name = "text")
    private String text;

    @Getter
    @Column(name = "completed")
    private Boolean completed;

    public Task(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
    }

    public Task() {

    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}