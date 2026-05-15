import java.time.LocalDateTime;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Task {

    // Fields
    private final String id;
    private String name;
    private boolean completed;
    @JsonIgnore
    private Task parent;
    private ArrayList<Task> subtasks;

    // Constructors
    public Task(String name) {
        id = LocalDateTime.now().toString();
        this.name = name;
        completed = false;
        parent = null;
        subtasks = new ArrayList<>();
    }

    // Public methods
    public void updateCompletion() {
        this.completed = !(this.completed);
    }

    public void addSubtask(Task subtask) {
        subtasks.add(subtask);
        subtask.parent = this;
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

    public Task getParent() {
        return parent;
    }

    public ArrayList<Task> getSubtasks() {
        return subtasks;
    }

    //Setters
    public void setTaskName(String newName) {
        this.name = newName;
    }
}
