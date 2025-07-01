package com.Controller;

import com.Entite.Categorie;
import com.Entite.Employe;
import com.Repository.CategorieRepository;
import com.Service.CategorieService;
import com.Service.EmployeService;
import com.Service.FilmService;

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
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmployeService employeService;
    // Formulaire: insert, update
    @GetMapping("/form-login")
    public String form(Model model) {
        return "user/form-login";
    }
    // Liste
    @PostMapping("/login")
    public String login(Model model, HttpSession session, @RequestParam(name = "user", required = true) String user, @RequestParam(name = "password", required = true) String password) {
        Employe target = this.employeService.login(user, password);
        if(target != null) {
            session.setAttribute("auth", "true");
            return "redirect:../";
        } 
        return "redirect:form-login";
    }
}