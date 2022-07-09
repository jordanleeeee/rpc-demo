package app;

import api.GoodByeWebService;
import framework.annotation.Service;

/**
 * @author Jordan
 */
@Service
public class GoodByeWebServiceImpl implements GoodByeWebService {
    @Override
    public String goodByeMessage() {
        return "goodbye";
    }
}
