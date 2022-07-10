package framework.proxyclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Jordan
 */
public class RpcProxyClient implements ProxyClient {
    private final InvocationHandler handler;

    public RpcProxyClient(InvocationHandler handler) {
        this.handler = handler;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T clientProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            handler
        );
    }
}
