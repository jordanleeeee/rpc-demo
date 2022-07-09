import api.GoodByeWebService;
import api.GreetWebService;
import framework.proxyclient.RpcByteBuddyProxyClient;

/**
 * @author Jordan
 */
public class GreetingClientDemo {
    public static void main(String[] args) {
        RpcByteBuddyProxyClient rpcProxyClient = new RpcByteBuddyProxyClient("localhost", 8080);
//        RpcProxyClient rpcProxyClient = new RpcProxyClient("localhost", 8080);
        GreetWebService greetWebService = rpcProxyClient.clientProxy(GreetWebService.class);
        GoodByeWebService goodByeWebService = rpcProxyClient.clientProxy(GoodByeWebService.class);

        System.out.println("greeting app is running\n");
        System.out.println(greetWebService.greeting());
        System.out.println(greetWebService.greeting("jordan"));
        System.out.println(goodByeWebService.goodByeMessage());
    }
}
