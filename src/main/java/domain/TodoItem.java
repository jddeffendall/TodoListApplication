package domain;

public class TodoItem {

    private int id;
    private String owner;
    private String description;
    private String created;
    private String due;
    private boolean isCompleted = false;
    private boolean isOverdue = false;

    public TodoItem(String description, String created, String due) {
        this.description = description;
        this.created = created;
        this.due = due;
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
        return created;
    }

    public String getDueDate() {
        return due;
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
