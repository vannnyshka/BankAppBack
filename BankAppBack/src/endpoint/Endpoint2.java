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

public class Endpoint2 extends BaseEndpoint implements HttpHandler {

    public static Connection connection;
    public static ResultSet resultSet;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

      String requestParamValue=null;
      String requestURI = httpExchange.getRequestURI().toString();
      System.out.println(requestURI);
      System.out.println(httpExchange.getRequestMethod());
      if("GET".equals(httpExchange.getRequestMethod())) {

          System.out.println("Endpoint2: GET handled");

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
          Account account = null;
          String id = null,status = null,fname = null,lname = null,passport = null ,password = null;
          String jsonString = requestParamValue ; //assign your JSON String here
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

          try {

              Class.forName("com.mysql.cj.jdbc.Driver");
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "iridos09");

              Statement statement = connection.createStatement();

              String query = "INSERT INTO BANK_ACCOUNTS (BANK_ACCNO, BANK_ACC, STATUS, "
                      + "FNAME, LNAME, PASS_ID, PASSWORD) VALUES (?,?,?,?,?,?,?)";

              PreparedStatement ps = connection.prepareStatement(query);

              ps.setString(1,id);
              ps.setDouble(2, 0);
              ps.setString(3,status.toUpperCase());
              ps.setString(4,fname.toUpperCase());
              ps.setString(5,lname.toUpperCase());
              ps.setString(6,passport.toUpperCase());
              ps.setString(7,password);
              ps.executeUpdate();

          } catch (SQLException e) {
              e.printStackTrace();
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }


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