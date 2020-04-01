package domain;

import java.time.LocalDateTime;

public class TodoItem {

    private int id;
    private String owner;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;

    public TodoItem(String description, LocalDateTime dueDate) {
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void snooze(LocalDateTime dueDate, int days) {
        dueDate = dueDate.plusDays(days);
    }

}
