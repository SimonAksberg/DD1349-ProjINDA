import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class ListHandler implements HttpHandler {

    // Fields
    private List<ToDoList> lists;
    private ObjectMapper mapper = new ObjectMapper();

    // Constructors
    public ListHandler(List<ToDoList> lists) {
        this.lists = lists;
    }

    // Public methods and connected helpers
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] parts = path.split("/");
        if (parts.length > 4 && parts[4].equals("tasks")) {
            handleTasks(exchange, parts[3]);
            return;  
        }

        // GET
        if (exchange.getRequestMethod().equals("GET")) {
            String response = mapper.writeValueAsString(lists);
                    
            sendJsonResponse(exchange, response);
            return;
        }

        // POST
        if (exchange.getRequestMethod().equals("POST")) {
            String name = getRequestBodyString(exchange);

            lists.add(new ToDoList(name));

            String response = "List added";

            sendResponse(exchange, response);
            return;
        } 

        // PUT
        if (exchange.getRequestMethod().equals("PUT")) {
            String listData = getRequestBodyString(exchange);

            String[] separatedListData = listData.split(",");
            String listId = separatedListData[0];
            String newName = separatedListData[1];

            ToDoList searchedList = findListById(listId);
            searchedList.setListName(newName);

            String response = "List renamed";

            sendResponse(exchange, response);
            return;
        }

        // DELETE
        if (exchange.getRequestMethod().equals("DELETE")) {
            String listId = getRequestBodyString(exchange);

            ToDoList searchedList = findListById(listId);
            lists.remove(searchedList);

            String response = "List deleted";

            sendResponse(exchange, response);
            return;
        }  
    }

    private void handleTasks(HttpExchange exchange, String listId) throws IOException {
        // GET
        if (exchange.getRequestMethod().equals("GET")) {
            ToDoList list = findListById(listId);
            String response = mapper.writeValueAsString(list.getTasksList());
                    
            sendJsonResponse(exchange, response);
            return;
        }

        // POST
        if (exchange.getRequestMethod().equals("POST")) {
            CreateTaskRequest request = mapper.readValue(exchange.getRequestBody(), CreateTaskRequest.class);
            
            String name = request.getTaskName();
            Task newTask = findListById(listId).addTask(name);

            if (request.getParentTaskId() != null) {
                Task parent = findTaskById(listId, request.getParentTaskId());
                parent.addSubtask(newTask);
            }

            String response = "Task added";
        
            sendResponse(exchange, response);
            return;
        }

        // PUT
        if (exchange.getRequestMethod().equals("PUT")) {
            String taskData = getRequestBodyString(exchange);

            String[] separatedTaskData = taskData.split(",");
            String taskId = separatedTaskData[1];
            Task searchedTask = findTaskById(listId, taskId);
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

            sendResponse(exchange, response);
            return;
        }

        // DELETE
        if (exchange.getRequestMethod().equals("DELETE")) {
            String taskId = getRequestBodyString(exchange);

            findListById(listId).getTasksList().removeIf(task -> task.getId().equals(taskId));

            String response = "Task deleted";

            sendResponse(exchange, response);
            return;
        }
    }

    // General private helper methods
    private ToDoList findListById(String listId) {
        for (ToDoList list : lists) {
            if(list.getListId().equals(listId)) {
                return list;
            } 
        }
        return null;
    }

    private Task findTaskById(String listId, String taskId) {
        ArrayList<Task> taskList = findListById(listId).getTasksList();

        for(Task task : taskList) {
            if(task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
    }

    private void sendJsonResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, response);
    }

    private String getRequestBodyString(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes());
    }
}