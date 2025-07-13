package com.Controller;

import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.TypePret;
import com.Entite.User;
import com.Service.PretParametreViewService;
import com.Service.PretService;
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
@RequestMapping("/pret")
public class PretController {
    @Autowired
    private PretService pretService;
    @Autowired
    private TypePretService typePretService;
    @Autowired
    private PretParametreViewService pretParametreViewService;

    @GetMapping("/liste-pret")
    public String liste(Model model,
            @RequestParam(value = "succes", required = false) String succes,
            @RequestParam(value = "error", required = false) String error) {
        if (succes != null) {
            model.addAttribute("succes", succes);
        } else if (error != null) {
            model.addAttribute("error", error);
        }
        List<PretParametreView> listePret = this.pretParametreViewService.findAll();
        model.addAttribute("listePret", listePret);
        model.addAttribute("page", "pret/liste-pret");
        return "template";
    }

    @GetMapping("/form-pret")
    public String form(Model model,
            @RequestParam(value = "succes", required = false) String succes,
            @RequestParam(value = "error", required = false) String error) {
        if (succes != null) {
            model.addAttribute("succes", succes);
        } else if (error != null) {
            model.addAttribute("error", error);
        }
        List<TypePret> listeTypePret = this.typePretService.findAll();
        model.addAttribute("listeTypePret", listeTypePret);
        model.addAttribute("page", "pret/form-pret");
        return "template";
    }

    @PostMapping("/creer")
    @ResponseBody
    public ResponseEntity<String> creerPret(@RequestParam("idUser") Long idUser,
            @RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam("idTypePret") Integer idTypePret, Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            pretService.preterUnExemplaireLivre(idUser, idEmp, idExemplaire, idTypePret);
            model.addAttribute("message", "Prêt créé avec succès !");
            return ResponseEntity.ok("Prêt créé avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du prêt : " + e.getMessage());
            return ResponseEntity.ok("Erreur lors de la création du prêt : " + e.getMessage());
        }
    }

    // Creer un pret a une date donnee
    @PostMapping("/creer-date-rest")
    @ResponseBody
    public ResponseEntity<String> creerPretAUneDateRest(@RequestParam("idUser") Long idUser,
            @RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("idTypePret") Integer idTypePret, Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            pretService.preterUnExemplaireLivre(idUser, idEmp, idExemplaire, idTypePret, date);
            model.addAttribute("message", "Prêt créé avec succès !");
            return ResponseEntity.ok("Prêt créé avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création du prêt : " + e.getMessage());
            return ResponseEntity.ok("Erreur lors de la création du prêt : " + e.getMessage());
        }
    }

    // Creer un pret a une date donnee
    @PostMapping("/creer-date")
    public String creerPretAUneDate(@RequestParam("idUser") Long idUser,
            @RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("idTypePret") Integer idTypePret, Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            pretService.preterUnExemplaireLivre(idUser, idEmp, idExemplaire, idTypePret, date);
            return "redirect:/pret/form-pret?succes=" + "Pree cree avec succes!";
        } catch (Exception e) {
            return "redirect:/pret/form-pret?error=" + "Erreur lors de la creation du pret : " + e.getMessage();
        }
    }

    @GetMapping(value = "penalite", produces = "application/json")
    @ResponseBody
    public ResponseEntity<PenaliteResponse> estPenalise(@RequestParam(name = "idInscription") Long idInscription)
            throws Exception {
        try {
            PenaliteResponse result = this.pretService.subitPenalite(idInscription);
            String temp = "Penalisation en cours";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
            // TODO: handle exception
        }
    }

    @GetMapping(value = "quota", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> quota(@RequestParam(name = "idUser") Long idUser)
            throws Exception {
        try {
            int result = this.pretService.quotaNonNullFromUserId(idUser);
            return ResponseEntity.ok("restant = " + result);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
            // TODO: handle exception
        }
    }
}