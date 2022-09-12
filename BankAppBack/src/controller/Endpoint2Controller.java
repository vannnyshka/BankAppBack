package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.Account;
import org.json.*;
import service.BankAccountService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.List;

public class Endpoint2Controller extends BaseEndpointController implements HttpHandler {

    public static Connection connection;
    private BankAccountService bankAccountService;

    public Endpoint2Controller() {
        this.bankAccountService = new BankAccountService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue = null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());
        if ("GET".equals(httpExchange.getRequestMethod())) {
            System.out.println("Endpoint2: GET handled");

            List<String> accounts = bankAccountService.getAllAccountsJson();

            requestParamValue = accounts.toString();
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            System.out.println("bank: Post handled");

            requestParamValue = handlePostRequest(httpExchange);

            System.out.println("Param:" + requestParamValue);

            Account account = null;
            String id = null, status = null, fname = null, lname = null, passport = null, password = null;
            String jsonString = requestParamValue; //assign your JSON String here

            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonString);
                id = obj.getString("id");
                status = obj.getString("status");
                fname = obj.getString("fname");
                lname = obj.getString("lname");
                passport = obj.getString("passport");
                password = obj.getString("password");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            bankAccountService.save(new Account(id, status, fname, lname, passport, password));

        } else {
            System.out.println("Endpoint2: Nothing handled");
        }
        handleResponse(httpExchange, requestParamValue);
    }

    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {
        BufferedReader httpInput = new BufferedReader(new InputStreamReader(
                httpExchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        httpInput.close();
        return in.toString().trim();
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        // StringBuilder htmlBuilder = new StringBuilder();
        // htmlBuilder.append("<html>").
        //         append("<body>").
        //         append("<h1>").
        //         append("Hello ")
        //         .append(requestParamValue)
        //         .append("</h1>")
        //         .append("</body>")
        //         .append("</html>");
        // String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());
        String htmlResponse = requestParamValue;

        super.setHttpExchangeResponseHeaders(httpExchange);

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}