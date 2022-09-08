package endpoint;

import com.sun.net.httpserver.*;
// import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import endpoint.BaseEndpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Endpoint3 extends BaseEndpoint implements HttpHandler {

    public static Connection connection;
    public static ResultSet resultSet;


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        //ArrayList<String> requestParamValue = new ArrayList<>();
        String requestParamValue=null;
        String requestURI = httpExchange.getRequestURI().toString();
        System.out.println(requestURI);
        System.out.println(httpExchange.getRequestMethod());
        if("GET".equals(httpExchange.getRequestMethod())) {

            System.out.println("Endpoint3: GET handled");

            String trancs = null;

            try {         //РАБОЧИЙ КОД

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "iridos09");

                Statement statement = connection.createStatement();

                resultSet = statement.executeQuery("SELECT * FROM  TRANSACTION");
                while (resultSet.next()) {
                    trancs += " " + Double.toString(resultSet.getInt(1))+ " " +resultSet.getString(2) + " " + resultSet.getString(3) + "  " +
                            resultSet.getDate(4) + " " + resultSet.getTime(5)+ "\n";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            requestParamValue = trancs.replace("null","");

        }
        else if("POST".equals(httpExchange.getRequestMethod())) {
            System.out.println("Endpoint2: Post handled");
            requestParamValue = handlePostRequest(httpExchange);
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