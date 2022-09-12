import com.sun.net.httpserver.*;
import controller.*;

import java.net.InetSocketAddress;
import java.util.concurrent.*;


public class Server {


    public static void main(String[] args) throws Exception {


        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(28888), 0);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(1);
        HttpContext context = server.createContext("/", new Endpoint1Controller());
        context = server.createContext("/endpoint2", new Endpoint2Controller());
        context = server.createContext("/endpoint3", new Endpoint3Controller());
        context = server.createContext("/balance", new BalanceController());
        context = server.createContext("/balancewirthdraw", new BalanceWirthDrawController());
        context = server.createContext("/transactions", new TransactionsConroller());
        context = server.createContext("/status", new StatusController());
        server.setExecutor(threadPoolExecutor);
        server.start();
        System.out.println("Server started");
    }
    
        
}