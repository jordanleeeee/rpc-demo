import api.GreetWebService;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author Jordan
 */
public class Demo {
    public static void main(String[] args) {
        GreetWebService webService = (GreetWebService) Proxy.newProxyInstance(GreetWebService.class.getClassLoader(),
            new Class<?>[]{GreetWebService.class},
            (proxy, method, arg) -> {
                System.out.println(method);
                System.out.println(Arrays.toString(arg));
                // serialize request
                // establish connection
                // send request
                // receive response
                // deserialize response
                // return response
                return "result from remote server";
            }
        );

        System.out.println("\ncall greeting()");
        System.out.println(webService.greeting());

        System.out.println("\ncall greeting(person)");
        System.out.println(webService.greeting("jordan"));
    }
}














/*
(GreetWebService) Proxy.newProxyInstance(GreetWebService.class.getClassLoader(),
            new Class<?>[]{GreetWebService.class},
            (proxy, method, arg) -> {
                System.out.println(method);
                System.out.println(Arrays.toString(arg));
                // serialize request
                // establish connection
                // send request
                // receive response
                // deserialize response
                // return response
                return "result from remote server";
            }
        );
 */
