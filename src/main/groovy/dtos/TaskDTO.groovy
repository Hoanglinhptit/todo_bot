package dtos
import groovy.transform.Canonical

@Canonical
class TaskDTO {
    private Long id;
    private Long chatId;
    private String text;
    private boolean completed;

    // getters and setters
    Long getId() {
        return id
    }

    void setId(Long id) {
        this.id = id
    }

    Long getChatId() {
        return chatId
    }

    void setChatId(Long chatId) {
        this.chatId = chatId
    }

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }

    boolean getCompleted() {
        return completed
    }

    void setCompleted(boolean completed) {
        this.completed = completed
    }
}
