import java.time.LocalDateTime;
import java.util.ArrayList;

public class ToDoList {
    private String name; 
    private ArrayList<Task> tasksList;

    private static class Task {
        private final LocalDateTime creationDate;
        public String name;
        public boolean completed;
        public Task subtask;

        public Task(String name) {
            creationDate = LocalDateTime.now();
            this.name = name;
            completed = false;
            subtask = null;
        }
    }

    public ToDoList(String name) {
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
}

