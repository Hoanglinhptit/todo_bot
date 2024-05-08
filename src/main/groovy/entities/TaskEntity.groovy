//package entities
//
//import groovy.transform.builder.Builder
//import jakarta.persistence.Entity
//import jakarta.persistence.GeneratedValue
//import jakarta.persistence.GenerationType
//import jakarta.persistence.Id
//import jakarta.persistence.Table
//
//@Entity
//@Builder
//@Table(name = "task")
//class TaskEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long userId;
//    private String text;
//    private boolean completed;
//
//    // getters and setters
//    Long getId() {
//        return id
//    }
//
//    void setId(Long id) {
//        this.id = id
//    }
//
//    Long getUserId() {
//        return userId
//    }
//
//    void setUserId(Long userId) {
//        this.userId = userId
//    }
//
//    String getText() {
//        return text
//    }
//
//    void setText(String text) {
//        this.text = text
//    }
//
//    boolean getCompleted() {
//        return completed
//    }
//
//    void setCompleted(boolean completed) {
//        this.completed = completed
//    }
//}
