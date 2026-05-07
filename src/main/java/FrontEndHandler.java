import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class FrontEndHandler implements HttpHandler {
    private final byte[] html;

    public FrontEndHandler() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/index.html")) {
            if (is == null) {
                throw new RuntimeException("index.html not found");
            }

            this.html = is.readAllBytes();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, html.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(html);
        } finally {
            exchange.close();
        }
    }
}