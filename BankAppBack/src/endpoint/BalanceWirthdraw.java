package endpoint;

import org.json.*;
import com.mysql.cj.xdevapi.JsonParser;
import com.sun.net.httpserver.*;
// import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import data.Account;
import endpoint.BaseEndpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import data.BalanceTop;

public class BalanceWirthdraw extends BaseEndpoint implements HttpHandler {

    public static Connection connection;
    public static ResultSet resultSet;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue=null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());


        if("GET".equals(httpExchange.getRequestMethod())) {
            //requestParamValue = requestParamValue;
        }

        else if("POST".equals(httpExchange.getRequestMethod())) {

            BalanceTop massage = new BalanceTop("Balance withdraw successfully");

            System.out.println("bank: Post handled");

            requestParamValue = handlePostRequest(httpExchange);

            System.out.println("Param:"+requestParamValue);

            String id = null,money = null;
            String jsonString = requestParamValue ; //assign your JSON String here
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonString);
                id = obj.getString("id");
                money = obj.getString("money");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "iridos09");

                Statement statement = connection.createStatement();

                String query = "UPDATE BANK_ACCOUNTS SET BANK_ACC = BANK_ACC - ?"
                        + "WHERE BANK_ACCNO = ?";

                PreparedStatement ps = connection.prepareStatement(query);

                Double money1 = Double.valueOf(money);
                ps.setDouble(1,money1);
                ps.setString(2, id);
                ps.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            requestParamValue= massage.convert();
            BalanceWirthdraw account = new BalanceWirthdraw();
            account.tranc_add(id, "WITHDRAWAL FROM A BANK ACCOUNT");

        }
        else{
            System.out.println("Endpoint2: Nothing handled");
        }
        handleResponse(httpExchange,requestParamValue);
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

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        System.out.println(requestParamValue);

        String htmlResponse = requestParamValue;

        super.setHttpExchangeResponseHeaders(httpExchange);

        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void tranc_add(String bank_account, String message) {

        try {

            String query = "INSERT INTO TRANSACTION (BANK_ACCNO, TRANC_NAME, TRANC_DATE, "
                    + "TRANC_TIME) VALUES (?,?, CURRENT_DATE(), CURRENT_TIME())";

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1,bank_account);
            ps.setString(2,message);
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
}

