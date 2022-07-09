package framework.network;

import api.RpcRequest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author Jordan
 */
public class ProcessHandler implements Runnable {
    private final Map<String, Object> services;
    private final Socket socket;

    public ProcessHandler(Map<String, Object> services, Socket socket) {
        this.services = services;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest request = (RpcRequest) inputStream.readObject();

            Method method = Class.forName(request.getClassName()).getMethod(request.getMethodName(), request.getTypes());
            Object response = method.invoke(services.get(request.getClassName()), request.getParams());
            outputStream.writeObject(response);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
