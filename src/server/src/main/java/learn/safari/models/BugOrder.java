package learn.safari.models;

public class BugOrder {

    private int bugOrderId;
    private String name;
    private String description;

    public int getBugOrderId() {
        return bugOrderId;
    }

    public void setBugOrderId(int bugOrderId) {
        this.bugOrderId = bugOrderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
