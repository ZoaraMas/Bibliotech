package com.Controller;

import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
import com.Service.EtatProlongementService;
import com.Service.EtatReservationService;
import com.Service.PretService;
import com.Service.RemiseLivreService;
import com.Service.ReservationService;
import com.Service.TypePretService;
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
@RequestMapping("/etat-prolongement")
public class EtatProlongementController {
    @Autowired
    private EtatProlongementService etatProlongementService;

    // @GetMapping("/form-etat")
    // public String form(Model model) {
    // return "etat/form-etat";
    // }

    // @PostMapping("/creer-rest")
    // @ResponseBody
    // public ResponseEntity<String> ajouterEtatReservationRest(
    // @RequestParam("idProlongement") Long idProlongement,
    // @RequestParam("confirmer") Boolean confirmer,
    // @RequestParam(value = "commentaire", required = false) String commentaire,
    // Model model, HttpSession session) {
    // String customMessage = "Reservation VALIDEE";
    // if (!confirmer)
    // customMessage = "Reservation REFUSEE";
    // try {
    // Long idEmp = (Long) session.getAttribute("auth");
    // this.etatReservationService.ajouterEtatReservation(idEmp, idProlongement,
    // confirmer, commentaire);
    // model.addAttribute("message", customMessage + " avec succes");
    // return ResponseEntity.ok(customMessage);
    // } catch (Exception e) {
    // model.addAttribute("Erreur: " + customMessage + " non realise: " +
    // e.getMessage());
    // return ResponseEntity.ok("Erreur: " + customMessage + " non realise: " +
    // e.getMessage());
    // }
    // }

    @PostMapping("/creer")
    public String ajouterEtatProlongement(
            @RequestParam("idProlongement") Long idProlongement,
            @RequestParam("confirmer") Boolean confirmer,
            @RequestParam(value = "commentaire", required = false) String commentaire,
            Model model, HttpSession session) {
        String customMessage = "Prolongement VALIDEE";
        if (!confirmer)
            customMessage = "Prolongement REFUSEE";
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            this.etatProlongementService.preparerAjouterEtatProlongement(idEmp, idProlongement, confirmer, commentaire);
            this.etatProlongementService.ajouterEtatProlongementFinal(idEmp, idProlongement, confirmer, commentaire);
            String succes = "?succes= " + customMessage;
            return "redirect:/prolongement/liste-prolongement" + succes;
        } catch (Exception e) {
            String error = "?error= " + e.getMessage();
            return "redirect:/prolongement/liste-prolongement" + error;
        }
    }

}