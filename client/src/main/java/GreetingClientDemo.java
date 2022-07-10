import api.GoodByeWebService;
import api.GreetWebService;
import framework.proxyclient.RpcByteBuddyProxyClient;
import framework.proxyclient.RpcInvocationHandler;
import framework.proxyclient.RpcProxyClient;

/**
 * @author Jordan
 */
public class GreetingClientDemo {
    public static void main(String[] args) {
        RpcInvocationHandler handler = new RpcInvocationHandler("localhost", 8080);
        RpcProxyClient rpcProxyClient = new RpcProxyClient(handler);
//        RpcByteBuddyProxyClient rpcProxyClient = new RpcByteBuddyProxyClient(handler);
        GreetWebService greetWebService = rpcProxyClient.clientProxy(GreetWebService.class);
        GoodByeWebService goodByeWebService = rpcProxyClient.clientProxy(GoodByeWebService.class);

        System.out.println("greeting app is running\n");
        System.out.println(greetWebService.greeting());
        System.out.println(greetWebService.greeting("jordan"));
        System.out.println(goodByeWebService.goodByeMessage());
    }
}
