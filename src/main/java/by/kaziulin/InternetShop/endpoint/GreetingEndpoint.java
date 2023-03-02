package by.kaziulin.InternetShop.endpoint;

import by.kaziulin.InternetShop.service.GreetingService;
import by.kaziulin.InternetShop.ws.greetings.GetGreetingRequest;
import by.kaziulin.InternetShop.ws.greetings.GetGreetingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;

@Endpoint
public class GreetingEndpoint {

    public static final String NAMESPACE_URL = "http://kaziulin.by/InternetShop/ws/greetings";

    private final GreetingService greetingService;

    @Autowired
    public GreetingEndpoint(GreetingService greetingService) {
        this.greetingService  = greetingService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getGreetingRequest")
    @ResponsePayload // полезная нагрузка
    public GetGreetingResponse getGreeting(@RequestPayload GetGreetingRequest request) throws DatatypeConfigurationException {
        GetGreetingResponse response = new GetGreetingResponse();
        response.setGreeting(greetingService.generateGreeting(request.getName()));
        return response;
    }
}
