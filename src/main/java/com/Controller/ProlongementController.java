package com.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Entite.Prolongement;
import com.Entite.ProlongementPretView;
import com.Service.PretService;
import com.Service.ProlongementPretViewService;
import com.Service.ProlongementService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prolongement")
public class ProlongementController {
    @Autowired
    private ProlongementService prolongementService;
    @Autowired
    private PretService pretService;
    @Autowired
    private ProlongementPretViewService prolongementPretViewService;

    @GetMapping("/liste-prolongement")
    public String listeProlongement(Model model,
            @RequestParam(value = "succes", required = false) String succes,
            @RequestParam(value = "error", required = false) String error) {
        if (succes != null) {
            model.addAttribute("succes", succes);
        } else if (error != null) {
            model.addAttribute("error", error);
        }
        List<ProlongementPretView> liste = this.prolongementPretViewService.findAll();
        model.addAttribute("listeProlongement", liste);
        model.addAttribute("page", "prolongement/liste-prolongement");
        return "template";
    }

    @PostMapping("/creer-rest")
    @ResponseBody
    public ResponseEntity<String> creerProlongementRest(@RequestParam("idPret") Long idPret,
            @RequestParam("dateDemande") LocalDateTime dateDemande,
            @RequestParam("commentaire") String commentaire,
            Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            this.prolongementService.demanderProlongement(idPret, dateDemande, commentaire, idEmp);
            model.addAttribute("message", "Demande de reservation cree avec succes!");
            return ResponseEntity.ok("Demande de Prolongation cree avec succes!");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la demande de prolongation : " + e.getMessage());
            return ResponseEntity.ok("Erreur lors de la création de la demande de reservation: " + e.getMessage());
        }
    }

    @PostMapping("/creer")
    public String creerProlongement(@RequestParam("idPret") Long idPret,
            @RequestParam("dateDemande") LocalDateTime dateDemande,
            @RequestParam("commentaire") String commentaire,
            Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            this.prolongementService.demanderProlongement(idPret, dateDemande, commentaire, idEmp);
            String succes = "?succes=Demande de Prolongement cree avec succes!";
            return "redirect:../pret/liste-pret" + succes;
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la demande de prolongation : " + e.getMessage());
            String error = "?error=Erreur lors de la creation de la demande de prolongement: " + e.getMessage();
            return "redirect:../pret/liste-pret" + error;
        }
    }
}
