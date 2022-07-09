package app;

import api.GreetWebService;
import framework.annotation.Service;

/**
 * @author Jordan
 */
@Service
public class GreetWebServiceImpl implements GreetWebService {
    @Override
    public String greeting() {
        return "good morning";
    }

    @Override
    public String greeting(String person) {
        return "good morning " + person;
    }
}
