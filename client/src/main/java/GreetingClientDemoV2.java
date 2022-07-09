import api.GoodByeWebService;
import api.GreetWebService;
import framework.ClientApp;
import framework.annotation.Reference;

/**
 * @author Jordan
 */
public class GreetingClientDemoV2 extends ClientApp {
    public static void main(String[] args) {
        new GreetingClientDemoV2().start();
    }

    @Reference
    GreetWebService greetWebService;

    @Reference
    GoodByeWebService goodByeWebService;

    @Override
    public void run() {
        System.out.println("greeting app is running\n");
        System.out.println(greetWebService.greeting());
        System.out.println(greetWebService.greeting("jordan"));
        System.out.println(goodByeWebService.goodByeMessage());
    }
}
