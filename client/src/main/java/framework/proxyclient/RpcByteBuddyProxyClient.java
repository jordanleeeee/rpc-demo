package framework.proxyclient;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

/**
 * @author Jordan
 */
public class RpcByteBuddyProxyClient implements ProxyClient {
    private final RpcInvocationHandler handler;

    public RpcByteBuddyProxyClient(RpcInvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public <T> T clientProxy(Class<T> interfaceClass) {
        try {
            return new ByteBuddy()
                    .subclass(interfaceClass)
                    .method(isDeclaredBy(interfaceClass))
                    .intercept(InvocationHandlerAdapter.of(handler))
                    .make()
                    .load(interfaceClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
