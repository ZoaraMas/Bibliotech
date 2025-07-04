package com.Controller;

import com.Entite.User;
import com.Service.PretService;
import com.Service.UserService;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.ArrayList;
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

    @GetMapping(value = "penalite", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> exemplaireEstDisponible(@RequestParam(name = "idInscription") Integer idInscription)
            throws Exception {
        try {
            boolean result = this.pretService.exemplaireEstDisponible(idInscription.longValue());
            return ResponseEntity.ok("resultat trouve = " + result);
        } catch (Exception e) {
            return ResponseEntity.ok("Erreur = " + e.getMessage());
            // TODO: handle exception
        }
    }
}