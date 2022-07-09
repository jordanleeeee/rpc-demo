package framework;

import framework.annotation.Reference;
import framework.proxyclient.ProxyClient;
import framework.proxyclient.RpcByteBuddyProxyClient;
import framework.proxyclient.RpcProxyClient;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author Jordan
 */
public abstract class ClientApp {
    private ProxyClient proxyClient;
    private ProxyClient byteBuddyProxyClient;
    private final Properties properties = new Properties();

    public void start() {
        try {
            initClient();
            inject();
            run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initClient() throws IOException {
        properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
        String host = properties.getProperty("server.host", "localhost");
        int port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        proxyClient = new RpcProxyClient(host, port);
        byteBuddyProxyClient = new RpcByteBuddyProxyClient(host, port);
    }

    private void inject() throws IllegalAccessException {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            Reference annotation = field.getAnnotation(Reference.class);
            if (annotation != null) {
                ProxyClient targetProxyClient = annotation.byByteBuddy() ? byteBuddyProxyClient : proxyClient;
                field.setAccessible(true);
                field.set(this, targetProxyClient.clientProxy(field.getType()));
            }
        }
    }

    public abstract void run();
}
