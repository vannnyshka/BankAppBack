package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.*;
import service.BankAccountService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
//import data.BalanceTop;

public class StatusController extends BaseEndpointController implements HttpHandler {

    private BankAccountService bankAccountService;

    public StatusController() {
        this.bankAccountService = new BankAccountService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());


        if ("GET".equals(httpExchange.getRequestMethod())) {
            System.out.println("Status: GET handled");

            List<String> accounts = bankAccountService.getAllAccountsJson();

            requestParamValue = accounts.toString();
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            System.out.println("bank: Post handled");

            requestParamValue = handlePostRequest(httpExchange);

            System.out.println("Param:" + requestParamValue);

            String id = null;
            String status = null;

            String jsonString = requestParamValue; //assign your JSON String here
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonString);
                id = obj.getString("id");
                status = obj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            bankAccountService.updateStatusByACCNO(id, status);

            requestParamValue = "Status was changed";
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

        System.out.println(requestParamValue);

        String htmlResponse = "{\"key\": \"" + requestParamValue + "\"}";
        super.setHttpExchangeResponseHeaders(httpExchange);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
