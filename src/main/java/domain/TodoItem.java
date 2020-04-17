package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem {

    private String title;
    private String due;
    private String created;
    private String completed;
    private String owner;
    private String overdue;
    private String id;

    public TodoItem(String title, String owner, String created, String due, String completed,String overdue, String id) {
        this.title = title;
        this.owner = owner;
        this.created = created;
        this.due = due;
        this.completed = completed;
        this.overdue = overdue;
        this.id = id;
    }

    public TodoItem(String description, String created, String due) {
        this.title = description;
        this.created = created;
        this.due = due;
    }


    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedDate() {
        return created;
    }

    public String getDueDate() {
        return due;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue() {
        overdue = "true";
    }

    public String getCompleted() {
        return completed;
    }

}
