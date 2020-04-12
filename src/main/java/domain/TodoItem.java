package domain;

public class TodoItem {

    private String title;
    private String due;
    private String created;
    private String owner;
    private int id;

    private boolean isCompleted = false;
    private boolean isOverdue = false;

    public TodoItem(String title, String owner, String created, String due, int id) {
        this.title = title;
        this.owner = owner;
        this.created = created;
        this.due = due;
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
