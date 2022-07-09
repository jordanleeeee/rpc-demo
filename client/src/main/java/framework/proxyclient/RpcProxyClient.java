package framework.proxyclient;

import api.RpcRequest;
import framework.network.RpcNetTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Jordan
 */
public class RpcProxyClient implements InvocationHandler, ProxyClient {
    private final String host;
    private final int port;

    public RpcProxyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T clientProxy(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass},
            this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setTypes(method.getParameterTypes());

        var transport = new RpcNetTransport(host, port);
        return transport.send(request);
    }
}
