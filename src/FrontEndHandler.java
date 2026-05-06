import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class FrontEndHandler implements HttpHandler {
    private final byte[] html;

    public FrontEndHandler() throws IOException {
        this.html = Files.readAllBytes(Paths.get("index.html"));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, html.length);

        OutputStream os = exchange.getResponseBody();
        os.write(html);
        os.close();
    }
}