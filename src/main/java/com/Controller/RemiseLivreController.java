package com.Controller;

import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Service.PretService;
import com.Service.RemiseLivreService;
import com.Service.UserService;
import com.dto.PenaliteResponse;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/remise-livre")
public class RemiseLivreController {
    @Autowired
    private RemiseLivreService remiseLivreService;

    @GetMapping("/form-remise")
    public String form(Model model) {
        return "remise/form-remise";
    }

    @PostMapping("/creer-rest")
    @ResponseBody
    public ResponseEntity<String> creerRemiseLivreRest(@RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam(value = "date", required = false) LocalDateTime dateRemise,
            @RequestParam("commentaire") String commentaire,
            Model model, HttpSession session) {
        try {
            if (dateRemise == null) {
                dateRemise = LocalDateTime.now();
            }
            Long idEmp = (Long) session.getAttribute("auth");
            remiseLivreService.remettreUnExemplaireDeLivre(idExemplaire, idEmp, dateRemise, commentaire);
            return ResponseEntity.ok("Remise créé avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la remise : " + e.getMessage());
            return ResponseEntity.ok("Erreur lors de la création de la remise: " + e.getMessage());
        }
    }

    @PostMapping("/creer")
    public String creerRemiseLivre(@RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam(value = "date", required = false) LocalDateTime dateRemise,
            @RequestParam("commentaire") String commentaire,
            Model model, HttpSession session) {
        try {
            if (dateRemise == null) {
                dateRemise = LocalDateTime.now();
            }
            Long idEmp = (Long) session.getAttribute("auth");
            System.out.println("bleu");

            remiseLivreService.remettreUnExemplaireDeLivre(idExemplaire, idEmp, dateRemise, commentaire);
            System.out.println("aladin");

            String succes = "?succes= Remise effectue avec succes";
            return "redirect:../pret/liste-pret" + succes;
        } catch (Exception e) {
            String error = "?error=Erreur lors de la création de la remise : " + e.getMessage();
            return "redirect:../pret/liste-pret" + error;
        }
    }

}