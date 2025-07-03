package com.Controller;

import com.Entite.User;
import com.Repository.ExemplaireRepository;
import com.Service.ExemplaireService;
import com.Service.PretService;
import com.Service.UserService;
import com.dto.PretParametreDTO;
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
@RequestMapping("/exemplaire")
public class ExemplaireController {
    @Autowired
    private ExemplaireService exemplaireService;

    @GetMapping(value = "exemplaire-disponible", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Boolean> exemplaireEstDisponible(@RequestParam(name = "idExemplaire") Long idExemplaire)
            throws Exception {
        try {
            boolean result = this.exemplaireService.exemplaireDisponible(idExemplaire);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
            // TODO: handle exception
        }
    }

    // @GetMapping("exemplaire-disponible")
    // @Response
    // public boolean exemplaireEstDisponible(@RequestParam(name = "idExemplaire")
    // Long idExemplaire)
    // throws Exception {
    // boolean result = this.exemplaireService.exemplaireDisponible(idExemplaire);
    // return result;
    // }

}

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.Service.ExemplaireService;

// @RestController
// @RequestMapping("/exemplaire")
// public class ExemplaireController {

// @Autowired
// private ExemplaireService exemplaireService;

// @GetMapping("/exemplaire-disponible")
// public ResponseEntity<Boolean> exemplaireEstDisponible(
// @RequestParam(name = "idExemplaire") Long idExemplaire) {
// try {
// boolean result = this.exemplaireService.exemplaireDisponible(idExemplaire);
// return ResponseEntity.ok(result);
// } catch (Exception e) {
// return ResponseEntity.badRequest().build();
// }
// }
// }