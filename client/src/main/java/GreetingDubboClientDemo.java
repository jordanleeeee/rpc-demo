import api.GreetWebService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * @author Jordan
 */
public class GreetingDubboClientDemo {
    public static void main(String[] args) {
        ReferenceConfig<GreetWebService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
        reference.setInterface(GreetWebService.class);
        GreetWebService greetWebService = reference.get();

        System.out.println(greetWebService.greeting());
        System.out.println(greetWebService.greeting("jordan"));
    }
}
