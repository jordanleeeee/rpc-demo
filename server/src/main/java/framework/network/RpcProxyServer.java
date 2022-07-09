package framework.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jordan
 */
public class RpcProxyServer {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final Map<String, Object> services = new HashMap<>();
    private final int port;

    public RpcProxyServer(int port) {
        this.port = port;
    }

    public void addService(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length != 1) {
            throw new IllegalArgumentException("must have one and only one service interface");
        }
        services.put(interfaces[0].getName(), service);
    }

    public void listen() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessHandler(services, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
