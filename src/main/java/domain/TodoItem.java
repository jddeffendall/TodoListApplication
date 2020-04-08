package domain;

public class TodoItem {

    private int id;
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

    public TodoItem(String owner, String description, String created, String due, int id) {
        this.owner = owner;
        this.title = description;
        this.created = created;
        this.due = due;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
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
