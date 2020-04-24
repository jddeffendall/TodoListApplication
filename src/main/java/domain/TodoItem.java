package domain;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem {

    @DatabaseField()
    private String title;

    @DatabaseField()
    private String due;

    @DatabaseField()
    private String created;

    @DatabaseField()
    private String completed;

    @DatabaseField()
    private String overdue;

    @DatabaseField()
    @SerializedName("completed date")
    private String completedDate;

    @DatabaseField()
    private String owner;

    @DatabaseField(id = true)
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

    public TodoItem(String description, String due) {
        this.title = description;
        this.due = due;
    }

    public TodoItem() {}


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

    public void setCompleted() {
        completed = "true";
    }

    public void snooze(TodoItem item) {
        String oldDue = item.getDueDate();

        LocalDateTime oldDateTime = LocalDateTime.parse(oldDue, DateTimeFormatter.ofPattern("MM dd yyyy HH:mm"));
        LocalDateTime snoozedTime = oldDateTime.plusMinutes(15);

        String newDue = snoozedTime.format(DateTimeFormatter.ofPattern("MM dd yyyy HH:mm"));
        due = newDue;
    }
}
