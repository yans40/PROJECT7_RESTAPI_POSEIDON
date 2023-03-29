package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    @Autowired
    private final OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private UserService userService;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping(value = "/user/list")
    public String home(Model model) {
        log.info("display user list");
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(@NotNull Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, @NotNull BindingResult result, Model model) {
        if (!result.hasErrors()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.save(user);
            log.info("user sauvegardé avec password encodé");
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userService.delete(user);
        log.info("user deleted");
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
