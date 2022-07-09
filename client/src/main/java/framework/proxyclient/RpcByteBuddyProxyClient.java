package framework.proxyclient;

import api.RpcRequest;
import framework.network.RpcNetTransport;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

/**
 * @author Jordan
 */
public class RpcByteBuddyProxyClient implements ProxyClient {
    private final String host;
    private final int port;

    public RpcByteBuddyProxyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public <T> T clientProxy(Class<T> interfaceClass) {
        try {
            return new ByteBuddy()
                .subclass(interfaceClass)
                .method(isDeclaredBy(interfaceClass))
                .intercept(MethodDelegation.to(new Interceptor(host, port)))
                .make()
                .load(interfaceClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class Interceptor {
        private final String host;
        private final int port;

        public Interceptor(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @RuntimeType
        public Object invoke(@Origin Method method, @AllArguments Object[] args) {
            RpcRequest request = new RpcRequest();
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParams(args);
            request.setTypes(method.getParameterTypes());

            var transport = new RpcNetTransport(host, port);
            return transport.send(request);
        }
    }
}
