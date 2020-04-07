package domain;

import java.time.LocalDateTime;

public class TodoItem {

    private int id;
    private String owner;
    private String description;
    private String createdDate;
    private String dueDate;
    private boolean isCompleted = false;
    private boolean isOverdue = false;

    public TodoItem(String description, String created, String due) {
        this.description = description;
        this.createdDate = created;
        this.dueDate = due;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean getCompletionStatus() {
        return isCompleted;
    }

    public void completeItem() {
        isCompleted = true;
    }

    public void setOverdue() {
        isOverdue = true;
    }

    public boolean getOverdueStatus() {
        return isOverdue;
    }

}
