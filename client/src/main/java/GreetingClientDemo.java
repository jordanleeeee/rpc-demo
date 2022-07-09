import api.GoodByeWebService;
import api.GreetWebService;
import framework.proxyclient.RpcProxyClient;

/**
 * @author Jordan
 */
public class GreetingClientDemo {
    public static void main(String[] args) {
        RpcProxyClient rpcProxyClient = new RpcProxyClient("localhost", 8080);
        GreetWebService webService = rpcProxyClient.clientProxy(GreetWebService.class);
        GoodByeWebService webServiceV2 = rpcProxyClient.clientProxy(GoodByeWebService.class);

        System.out.println("greeting app is running\n");
        System.out.println(webService.greeting());
        System.out.println(webService.greeting("jordan"));
        System.out.println(webServiceV2.goodByeMessage());
    }
}
