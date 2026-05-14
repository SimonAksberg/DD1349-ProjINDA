import java.time.LocalDateTime;

public class Task {

    // Fields
    private final String id;
    private String name;
    private boolean completed;
    private Task subtask;

    // Constructors
    public Task(String name) {
        id = LocalDateTime.now().toString();
        this.name = name;
        completed = false;
        subtask = null;
    }

    // Public methods
    public void updateCompletion() {
        this.completed = !(this.completed);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Task getSubtask() {
        return subtask;
    }

    //Setters
    public void setTaskName(String newName) {
        this.name = newName;
    }
}
