import framework.network.RpcProxyServer;
import app.GreetWebServiceImpl;
import app.GoodByeWebServiceImpl;

/**
 * @author Jordan
 */
public class ServerDemo {
    public static void main(String[] args) {
        RpcProxyServer server = new RpcProxyServer(8080);
        server.addService(new GreetWebServiceImpl());
        server.addService(new GoodByeWebServiceImpl());
        server.listen();
    }
}
