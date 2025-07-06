package com.Controller;

import com.Entite.RemiseLivre;
import com.Entite.TypePret;
import com.Entite.User;
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
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private TypePretService typePretService;
    
    @GetMapping("/form-reservation")
    public String form(Model model) {
           List<TypePret> listeTypePret = this.typePretService.findAll();
        model.addAttribute("listeTypePret", listeTypePret);
        return "reservation/form-reservation";
    }

    @PostMapping("/creer")
    @ResponseBody
    public ResponseEntity<String> creerReservation(@RequestParam("idUser") Long idUser,
            @RequestParam("idExemplaire") Long idExemplaire,
            @RequestParam("idTypePret") Integer idTypePret,
            @RequestParam("dateCible") LocalDateTime dateCible,
            @RequestParam("commentaire") String commentaire,
            Model model, HttpSession session) {
        try {
            Long idEmp = (Long) session.getAttribute("auth");
            this.reservationService.demanderReservation(idUser, idEmp, idExemplaire, idTypePret, dateCible,
                    commentaire);
            model.addAttribute("message", "Demande de reservation cree avec succes!");
            return ResponseEntity.ok("Demande de reservation cree avec succes!");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création de la demande de reservation : " + e.getMessage());
            return ResponseEntity.ok("Erreur lors de la création de la demande de reservation: " + e.getMessage());
        }
    }

}