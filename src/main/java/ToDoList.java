import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoList {
    private final String id;
    private String name; 
    private ArrayList<Task> tasksList;

    public static class Task {
        private final String id;
        private String name;
        private boolean completed;
        private Task subtask;

        public Task(String name) {
            id = LocalDateTime.now().toString();
            this.name = name;
            completed = false;
            subtask = null;
        }

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
    }

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

    public void changeCompletion(Task t) {
        t.completed = !(t.completed);
    }

    public String getName() {
        return name;
    }

    public ArrayList<ToDoList.Task> getTasksList() {
        return tasksList;
    }

    public String getListId() {
        return id;
    }

    public void setListName(String newName) {
        name = newName;
    }
}

