import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoList {
    private final String id;
    private String name; 
    private ArrayList<Task> tasksList;

    public ToDoList(String name) {
        id = LocalDateTime.now().toString();
        this.name = name;
        tasksList = new ArrayList<>();
    }

    public void addTask(String name) {
        tasksList.add(new Task(name));
    }

    public void removeTask(Task t) {
        tasksList.remove(t);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    public String getListId() {
        return id;
    }

    public void setListName(String newName) {
        name = newName;
    }
}

