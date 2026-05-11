import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class FrontEndHandler implements HttpHandler {
    private final byte[] html;
    private final byte[] css;

    public FrontEndHandler() throws IOException {
        try (InputStream is = getClass().getResourceAsStream("/index.html")) {
            if (is == null) {
                throw new RuntimeException("index.html not found");
            }

            this.html = is.readAllBytes();
        }

        try (InputStream is = getClass().getResourceAsStream("/style.css")) {
            if (is == null) {
                throw new RuntimeException("style.css not found");
            }

            this.css = is.readAllBytes();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        byte[] response;
        String contentType;

        if (path.equals("/style.css")) {
            response = css;
            contentType = "text/css";
        } else {
            response = html;
            contentType = "text/html";
        }

        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        } finally {
            exchange.close();
        }
    }
}