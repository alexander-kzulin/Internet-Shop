package by.kaziulin.InternetShop.controller;

import by.kaziulin.InternetShop.dto.UserDTO;
import by.kaziulin.InternetShop.entity.User;
import by.kaziulin.InternetShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }
@PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model) {
    System.out.println("called method new user");
        model.addAttribute("user", new UserDTO());
        return "user";
    }
    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username) {
        System.out.println("called method getRoles");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }

    @PostMapping("/new")
    public String saveUser(UserDTO userDTO, Model model) {
        if (userService.save(userDTO)) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", userDTO);
            return "user";
        }
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You're not authorize 1");
        }
        User user = userService.findByName(principal.getName());

        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserDTO userDTO, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())) {
            throw new RuntimeException("You're not authorize 2");
        }
        if (userDTO.getPassword() != null
                && !userDTO.getPassword().isEmpty()
                && !Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            model.addAttribute("user", userDTO);
            // нужно добавить какое то сообщение. сделать похже
            return "profile";
        }
        userService.updateProfile(userDTO);
        return "redirect:/users/profile";
    }

}
