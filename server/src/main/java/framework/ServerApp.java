package framework;

import framework.annotation.Service;
import framework.network.RpcProxyServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author Jordan
 */
public abstract class ServerApp {
    private final Properties properties = new Properties();
    RpcProxyServer server;
    int port;

    public void start() {
        try {
            initServer();
            packageScanAndAddService(properties.getProperty("server.servicePackage"));
            startServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initServer() throws IOException {
        properties.load(this.getClass().getClassLoader().getResourceAsStream("app.properties"));
        port = Integer.parseInt(properties.getProperty("server.port", "8080"));
        server = new RpcProxyServer(port);
    }

    private void packageScanAndAddService(String scanPackage) throws Exception {
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        if (url == null) throw new IllegalArgumentException("no such package, package=" + scanPackage);
        File classPath = new File(url.getFile());

        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                packageScanAndAddService(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                loadWebService(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    private void loadWebService(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        if (!clazz.isAnnotationPresent(Service.class)) return;

        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length != 1 || constructors[0].getParameterCount() != 0) {
            throw new IllegalArgumentException("no public constructor class:" + clazz.getName());
        }

        Object service = constructors[0].newInstance();
        server.addService(service);
    }

    private void startServer() {
        System.out.println("server is listening on port: " + port);
        server.listen();
    }
}
