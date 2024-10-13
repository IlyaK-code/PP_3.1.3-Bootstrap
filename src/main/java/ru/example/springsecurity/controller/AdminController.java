package ru.example.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.springsecurity.models.User;
import ru.example.springsecurity.repo.RoleRepo;
import ru.example.springsecurity.repo.UserRepo;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String adminHomeAndAllUsers(Model model, Principal principal) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("roles", roleRepo.findAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/save")
    public String saveUser(@RequestParam("id") Long id, User user) {
        User existingUser = userRepo.findById(id).orElse(null);

        if (existingUser != null && (user.getPassword() == null || user.getPassword().isEmpty())) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (existingUser != null) {
            user.setId(existingUser.getId());
        }
        if (existingUser != null && (user.getRoles() == null || user.getRoles().isEmpty())) {
            user.setRoles(existingUser.getRoles());
        }

        userRepo.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(User user) {
        userRepo.delete(user);
        return "redirect:/admin";
    }
}
