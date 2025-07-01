package com.Controller;

import com.Entite.User;
import com.Service.UserService;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/unlog")
    public String unlog(Model model,  HttpSession session) {
        if(session.getAttribute("auth") != null) session.removeAttribute("auth");
        return "user/form-login";
    }

    // Formulaire: insert, update
    @GetMapping("/form-login")
    public String form(Model model) {
        return "user/form-login";
    }
    
    // Liste
    @PostMapping("/login")
    public String login(Model model, HttpSession session, @RequestParam(name = "user", required = true) String user, @RequestParam(name = "password", required = true) String password) {
        User target = this.userService.login(user, password);
        if(target != null) {
            session.setAttribute("auth", "true");
            System.out.println("cheese null");
            return "redirect:../";
        }
        System.out.println("cheese not null");
        model.addAttribute("error", "Mail or Password wrong or don't match");
        return "redirect:form-login";
    }
}