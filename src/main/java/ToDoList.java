import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoList {

    // Fields
    private final String id;
    private String name; 
    private ArrayList<Task> tasksList;

    // Constructors
    public ToDoList(String name) {
        id = LocalDateTime.now().toString();
        this.name = name;
        tasksList = new ArrayList<>();
    }

    // Public methods
    public void addTask(String name) {
        tasksList.add(new Task(name));
    }

    public void removeTask(Task t) {
        tasksList.remove(t);
    }

    // Getters
    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public String getListId() {
        return id;
    }

    // Setters
    public void setListName(String newName) {
        name = newName;
    }
}

