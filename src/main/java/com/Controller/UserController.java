package com.Controller;

import com.Entite.Inscription;
import com.Entite.User;
import com.Service.InscriptionService;
import com.Service.PretService;
import com.Service.UserService;
import com.dto.PenaliteResponse;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PretService pretService;
    @Autowired
    private InscriptionService inscriptionService;

    // Verifier l'etat de penalisation d'un use
    @GetMapping("/penalite")
    @ResponseBody
    public ResponseEntity<String> getPenalite(@RequestParam(name = "idUser") Long idUser,
            @RequestParam(name = "date") LocalDateTime date) throws Exception {
        Inscription currInscription = inscriptionService.getCurrentInscription(idUser);
        PenaliteResponse penaliteResponse = this.pretService.subitPenalite(currInscription.getId(), date);
        return ResponseEntity.ok(penaliteResponse.getMessage());
    }

    @GetMapping("/penalite-now")
    @ResponseBody
    public ResponseEntity<String> getPenaliteNow(@RequestParam(name = "idUser") Long idUser) throws Exception {
        Inscription currInscription = inscriptionService.getCurrentInscription(idUser);
        PenaliteResponse penaliteResponse = this.pretService.subitPenalite(currInscription.getId());
        return ResponseEntity.ok(penaliteResponse.getMessage());
    }

    @GetMapping("/unlog")
    public String unlog(Model model, HttpSession session) {
        if (session.getAttribute("auth") != null)
            session.removeAttribute("auth");
        return "user/form-login";
    }

    // Formulaire: insert, update
    @GetMapping("/form-login")
    public String form(Model model) {
        return "user/form-login";
    }

    // Liste
    @PostMapping("/login")
    public String login(Model model, HttpSession session, @RequestParam(name = "user", required = true) String user,
            @RequestParam(name = "password", required = true) String password) {
        User target = this.userService.login(user, password);
        if (target != null) {
            session.setAttribute("auth", target.getId());
            System.out.println("cheese null");
            return "redirect:../";
        }
        System.out.println("cheese not null");
        model.addAttribute("error", "Mail or Password wrong or don't match");
        return "redirect:form-login";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        return "user/hello";
    }
}