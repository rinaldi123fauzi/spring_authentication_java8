package com.rinaldi.spring_authentication.controller;

import com.rinaldi.spring_authentication.model.User;
import com.rinaldi.spring_authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository repo;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/list_users")
    public String viewListUsers(Model model){
        List<User> listUsers = repo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/about")
    public String viewAbout(Model model){
        return "about";
    }

    @GetMapping("/register")
    public String viewSignUp(Model model){
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
        return "redirect:/list_users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id){
        //call delete employee method
        this.repo.deleteById(id);
        return "redirect:/list_users";
    }
}
