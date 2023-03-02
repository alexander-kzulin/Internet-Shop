package by.kaziulin.InternetShop.service;

import by.kaziulin.InternetShop.ws.greetings.Greeting;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

@Component
public class GreetingService {
    public Greeting generateGreeting(String name) throws DatatypeConfigurationException {
        Greeting greeting = new Greeting();
        greeting.setText("Hello " + name);
        greeting.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        return greeting;
    }

}
