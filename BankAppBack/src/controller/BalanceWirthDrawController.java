package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.BalanceTop;
import org.json.*;
import service.BankAccountService;
import service.TransactionalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class BalanceWirthDrawController extends BaseEndpointController implements HttpHandler {

    private BankAccountService bankAccountService;
    private TransactionalService transactionalService;

    public BalanceWirthDrawController() {
        this.bankAccountService = new BankAccountService();
        this.transactionalService = new TransactionalService();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());


        if ("GET".equals(httpExchange.getRequestMethod())) {
            //requestParamValue = requestParamValue;
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            BalanceTop massage = new BalanceTop("Balance withdraw successfully");

            System.out.println("bank: Post handled");

            requestParamValue = handlePostRequest(httpExchange);

            System.out.println("Param:" + requestParamValue);

            String id = null, money = null;
            String jsonString = requestParamValue; //assign your JSON String here

            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonString);
                id = obj.getString("id");
                money = obj.getString("money");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bankAccountService.reduceMoney(id, money);

            requestParamValue = massage.convert();
            transactionalService.save(id, "WITHDRAWAL FROM A BANK ACCOUNT");
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

        String htmlResponse = requestParamValue;

        super.setHttpExchangeResponseHeaders(httpExchange);

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}

