package org.example.by.tms.homeWork.leasson22;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public class HistoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().toString();
        File history = new File("history.txt");
        if (query.equals("/history?clear")) {
            new FileOutputStream(history).close();
            exchange.sendResponseHeaders(200, "true".length());
            exchange.getResponseBody().write("True".getBytes());
            exchange.close();
        } else  {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(history))) {
                exchange.sendResponseHeaders(200, history.length());
                exchange.getResponseBody().write(bufferedInputStream.readAllBytes());
                exchange.close();
            } catch (IOException ioe) {
                throw new IOException(ioe);
            }
        }
    }

}
