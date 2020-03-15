package ru.javamentor.onlineoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.entity.UserRole;
import ru.javamentor.onlineoffice.service.UserRoleService;
import ru.javamentor.onlineoffice.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
	@Autowired
    private UserService userService;

	@Autowired
    private UserRoleService userRoleService;

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("registration");
        User newUser = new User();
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRoleService.findByRole("ROLE_USER"));
        newUser.setRoles(roles);
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping(value = "/registration")
    public String registerNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping(value = "/office")
    public String office() {
        return "office";
    }
}
