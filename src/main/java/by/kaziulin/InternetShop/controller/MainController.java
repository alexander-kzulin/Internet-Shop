package by.kaziulin.InternetShop.controller;

import by.kaziulin.InternetShop.service.SessionObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class MainController {


    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession session) {
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
        if (session.getAttribute("myID") == null) {
            String uuid = UUID.randomUUID().toString();
            session.setAttribute("myID", uuid);
            System.out.println("Generated UUID -> " + uuid);
        }
        model.addAttribute("uuid", session.getAttribute("myID"));
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
