import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

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
            Iterator <ToDoList> iterator = lists.iterator();
            
            while (iterator.hasNext()) {
                ToDoList list = iterator.next();
                if (list.getListId().equals(listId)) {
                    iterator.remove();
                }
            }

            String response = "List deleted";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();
            return;
        }
    }
}