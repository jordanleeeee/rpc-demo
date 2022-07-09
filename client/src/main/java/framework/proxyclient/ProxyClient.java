package framework.proxyclient;

/**
 * @author Jordan
 */
public interface ProxyClient {
    <T> T clientProxy(Class<T> interfaceClass);
}
