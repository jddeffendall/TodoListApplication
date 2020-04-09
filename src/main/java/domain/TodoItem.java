package domain;

public class TodoItem {

    private String id;
    private String owner;
    private String title;
    private String created;
    private String due;


    private boolean isCompleted = false;
    private boolean isOverdue = false;

    public TodoItem(String description, String created, String due) {
        this.title = description;
        this.created = created;
        this.due = due;
    }

    public TodoItem(String title, String owner, String created, String due, String id) {
        this.title = title;
        this.owner = owner;
        this.created = created;
        this.due = due;
        this.id = id;
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

    public boolean getCompletionStatus() {
        return isCompleted;
    }

    public void setCompleted() {
        isCompleted = true;
    }

    public void setOverdue() {
        isOverdue = true;
    }

    public boolean getOverdueStatus() {
        return isOverdue;
    }

}
