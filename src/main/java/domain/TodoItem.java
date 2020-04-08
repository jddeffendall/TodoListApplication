package domain;

public class TodoItem {

    private int id;
    private String owner;
    private String title;
    private String created;
    private String due;
    private boolean isCompleted = false;
    private boolean isOverdue = false;

    public TodoItem(String owner, int id, String description, String created, String due) {
        this.owner = owner;
        this.id = id;
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
