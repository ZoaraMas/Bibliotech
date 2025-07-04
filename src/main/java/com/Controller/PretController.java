package com.Controller;

import com.Entite.User;
import com.Service.PretService;
import com.Service.UserService;
import com.dto.PenaliteResponse;
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
}