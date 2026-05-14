import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class ListHandler implements HttpHandler {

    private List<ToDoList> lists;
    private ObjectMapper mapper = new ObjectMapper();

    public ListHandler(List<ToDoList> lists) {
        this.lists = lists;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        if (parts.length > 4 && parts[4].equals("tasks")) {
            handleTasks(exchange, parts[3]);
            return;  
        }

        if (exchange.getRequestMethod().equals("POST")) {

            InputStream is = exchange.getRequestBody();
            String name = new String(is.readAllBytes());

            lists.add(new ToDoList(name));

            String response = "List added";
        
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("GET")) {
            String response = mapper.writeValueAsString(lists);
                    
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("DELETE")) {

            InputStream is = exchange.getRequestBody();
            String listId = new String(is.readAllBytes());

            ToDoList searchedList = findListById(listId);
            lists.remove(searchedList);

            String response = "List deleted";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("PUT")) {

            InputStream is = exchange.getRequestBody();
            String listData = new String(is.readAllBytes());

            String[] separatedListData = listData.split(",");
            String listId = separatedListData[0];
            String newName = separatedListData[1];

            ToDoList searchedList = findListById(listId);
            searchedList.setListName(newName);

            String response = "List renamed";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }
    }

    private void handleTasks(HttpExchange exchange, String listId) throws IOException {
        if (exchange.getRequestMethod().equals("POST")) {
            InputStream is = exchange.getRequestBody();
            String name = new String(is.readAllBytes());

            findListById(listId).addTask(name);

            String response = "Task added";
        
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("GET")) {
            ToDoList list = findListById(listId);
            String response = mapper.writeValueAsString(list.getTasksList());
                    
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("DELETE")) {
            InputStream is = exchange.getRequestBody();
            String taskId = new String(is.readAllBytes());

            findListById(listId).getTasksList().removeIf(task -> task.getId().equals(taskId));

            String response = "Task deleted";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }

        if (exchange.getRequestMethod().equals("PUT")) {
            
            InputStream is = exchange.getRequestBody();
            String taskData = new String(is.readAllBytes());
            String[] separatedTaskData = taskData.split(",");

            String taskId = separatedTaskData[1];
            ToDoList.Task searchedTask = findTaskById(listId, taskId);
            String response = "";

            if(separatedTaskData[0].equals("rename")) { 

                String newName = separatedTaskData[2];
                searchedTask.setTaskName(newName);
                response = "Task renamed";

            } else if (separatedTaskData[0].equals("updateCompletion")) {
                searchedTask.updateCompletion();

                Boolean taskCompletionStatus = searchedTask.isCompleted();

                if(taskCompletionStatus == true) {
                    response = "Task completed";
                } else {
                    response = "Task uncompleted";
                }
            } else {
                response = "something went wrong";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }
    }

    private ToDoList findListById(String listId) {
        for (ToDoList list : lists) {
            if(list.getListId().equals(listId)) {
                return list;
            } 
        }
        return null;
    }

    private ToDoList.Task findTaskById(String listId, String taskId) {
        ArrayList<ToDoList.Task> taskList = findListById(listId).getTasksList();

        for(ToDoList.Task task : taskList) {
            if(task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }
    
}