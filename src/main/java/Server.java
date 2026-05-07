import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static List<ToDoList> toDoLists = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new FrontEndHandler());

        server.createContext("/api/lists", new ListHandler(toDoLists));

        server.start();
        System.out.println("Server running on http://localhost:8000");
    }
}