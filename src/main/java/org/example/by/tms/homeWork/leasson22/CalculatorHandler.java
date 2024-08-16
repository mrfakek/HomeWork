package org.example.by.tms.homeWork.leasson22;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CalculatorHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> parameters = getParameters(query);
        String result = "Result = %s";
        int num1 = Integer.parseInt(parameters.get("num1"));
        int num2 = Integer.parseInt(parameters.get("num2"));
String response  = switch (parameters.get("type")){
    case "sum" ->result.formatted(num1+num2);
    case "sub" ->result.formatted(num1-num2);
    case "mul" ->result.formatted(num1*num2);
    case "div" ->result.formatted(num1/num2);

    default -> throw new IllegalStateException("Unexpected value: " + parameters.get("type"));
};

        File history = new File("history.txt");
        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(history,true))) {
            bufferedOutputStream.write(
                    parameters
                            .toString()
                            .concat("\n")
                            .concat(response)
                            .concat("\n\n")
                            .getBytes()
            );
        }
        catch(IOException ioe) {
            throw new IOException(ioe);
        }
exchange.sendResponseHeaders(200, response.length());
exchange.getResponseBody().write(response.getBytes());
exchange.close();
    }

    private static Map<String, String> getParameters(String query) {
        Map<String, String> parameters = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] split1 = pair.split("=");
            parameters.put(split1[0], split1[1]);
        }
        return parameters;
    }
}
