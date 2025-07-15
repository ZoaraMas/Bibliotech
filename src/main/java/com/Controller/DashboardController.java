package com.Controller;

import com.Entite.PretNombreDescView;
import com.Entite.TypePret;
import com.Entite.User;
import com.Entite.UserCountPretDescView;
import com.Service.DashboardService;
import com.Service.PretService;
import com.Service.TypePretService;
import com.Service.UserService;
import com.dto.PenaliteResponse;
import com.Service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private PretService pretService;
    @Autowired
    private TypePretService typePretService;

    // Rest
    @GetMapping("/livre-les-plus-pretes-ws")
    @ResponseBody
    public List<PretNombreDescView> lesPlusPretesWs(Model model) throws Exception {
        List<PretNombreDescView> result = this.dashboardService.findLivresLesPlusPretes();
        return result;
    }

    @GetMapping("/livre")
    public String form(Model model) throws Exception {
        // List<TypePret> listeTypePret = this.typePretService.findAll();
        // model.addAttribute("listeTypePret", listeTypePret);
        List<PretNombreDescView> LivreLesPlusPretes = this.dashboardService.findLivresLesPlusPretes();
        model.addAttribute("listeLivre", LivreLesPlusPretes);

        // Les utilisateurs qui on le plus pretes
        List<UserCountPretDescView> userPlusPretes = this.dashboardService.findUserPlusPretes();
        model.addAttribute("listeUser", userPlusPretes);

        // Liste des utilisateurs actuellement penalise avec les messages respectifs
        HashMap<User, String> listeUtilisateurPenalise = this.dashboardService.getListeUserPenalise();
        model.addAttribute("listeUserPenalise", listeUtilisateurPenalise);

        model.addAttribute("page", "dashboard/stat-livre");
        return "template";
    }
}