package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TodoItem {

    private String title;
    private String due;
    private String created;
    private String completed;
    private String owner;
    private int id;

    private boolean isOverdue = false;

    public TodoItem(String title, String owner, String created, String due, String completed, int id) {
        this.title = title;
        this.owner = owner;
        this.created = created;
        this.due = due;
        this.completed = completed;
        this.id = id;
    }

    public TodoItem(String description, String created, String due) {
        this.title = description;
        this.created = created;
        this.due = due;
    }


    public int getId() {
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

    public void setOverdue() {
        isOverdue = true;
    }

    public boolean getOverdueStatus() {
        return isOverdue;
    }

    public void snooze(TodoItem item) {
        String dueString = item.getDueDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy HH:mm a");
        LocalDateTime dueDate = LocalDateTime.parse(dueString, formatter);
        dueDate.plusMinutes(10);
        String newDueDateString = dueDate.format(formatter);
        due = newDueDateString;
    }

    public String getCompleted() {
        return completed;
    }

}
