package com.Controller;

import com.Entite.TypePret;
import com.Entite.User;
import com.Service.PretService;
import com.Service.TypePretService;
import com.Service.UserService;
import com.dto.PenaliteResponse;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
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

    @GetMapping("/form-pret")
    public String form(Model model) {
        List<TypePret> listeTypePret = this.typePretService.findAll();
        model.addAttribute("listeTypePret", listeTypePret);
        return "pret/form-pret";
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