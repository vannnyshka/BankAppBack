import com.sun.net.httpserver.*;
// import org.apache.commons.text.StringEscapeUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;
import java.util.concurrent.*;

import endpoint.*;


public class Server {


    public static void main(String[] args) throws Exception {


        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(28888), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        HttpContext context = server.createContext("/", new Endpoint1());
        context = server.createContext("/endpoint2", new Endpoint2());
        context = server.createContext("/endpoint3", new Endpoint3());
        context = server.createContext("/balance", new Balance());
        context = server.createContext("/balancewirthdraw", new BalanceWirthdraw());
        context = server.createContext("/transactions", new Transactions());
        context = server.createContext("/status", new Status());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started");
    }
    
        
}