package domain;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem {

    private String title;
    private String due;
    private String created;
    private String completed;
    private String overdue;

    @SerializedName("completed date")
    private String completedDate;

    private String owner;
    private String id;

    public TodoItem(String title, String owner, String created, String due, String completed, String overdue, String id, String completedDate) {
        this.title = title;
        this.due = due;
        this.created = created;
        this.completed = completed;
        this.overdue = overdue;
        this.completedDate = completedDate;
        this.owner = owner;
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

    public String getCompletedDate() {
        return completedDate;
    }

}
