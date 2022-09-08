package endpoint;

import data.Account;
import org.json.*;
//import com.mysql.cj.xdevapi.JsonParser;
import com.sun.net.httpserver.*;
// import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.io.OutputStream;
//import java.net.InetSocketAddress;
import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//import data.BalanceTop;

public class Status extends BaseEndpoint implements HttpHandler {

    public static Connection connection;
    public static ResultSet resultSet;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue=null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());


        if ("GET".equals(httpExchange.getRequestMethod())) {
            System.out.println("Status: GET handled");
            List<String> accounts = new ArrayList<>();
            Account account = null;

            try {         //РАБОЧИЙ КОД

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "iridos09");

                Statement statement = connection.createStatement();

                resultSet = statement.executeQuery("SELECT BANK_ACCNO, BANK_ACC, STATUS, CLINO, "
                        + "SUBSTR(FNAME,1,8), SUBSTR(LNAME,1,10), PASS_ID, PASSWORD FROM BANK_ACCOUNTS ORDER BY CLINO");
                while (resultSet.next()) {

                    account = new Account(resultSet.getString(1),resultSet.getDouble(2),
                            resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5),
                            resultSet.getString(6),resultSet.getString(7),resultSet.getString(8));

                    accounts.add(account.convert());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            requestParamValue = accounts.toString();
        }

        else if("POST".equals(httpExchange.getRequestMethod())) {


            System.out.println("bank: Post handled");

            requestParamValue = handlePostRequest(httpExchange);

            System.out.println("Param:"+requestParamValue);

            String id = null,status = null;
            String jsonString = requestParamValue ; //assign your JSON String here
            JSONObject obj = null;
            try {
                obj = new JSONObject(jsonString);
                id = obj.getString("id");
                status = obj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "iridos09");

                Statement statement = connection.createStatement();

                String query = "UPDATE BANK_ACCOUNTS SET STATUS = ? "
                        + "WHERE BANK_ACCNO = ?";

                PreparedStatement ps = connection.prepareStatement(query);

                ps.setString(1,status);
                ps.setString(2, id);
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            requestParamValue="Status was changed";

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

        String htmlResponse = "{\"key\": \"" + requestParamValue + "\"}";
        super.setHttpExchangeResponseHeaders(httpExchange);
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }

}
