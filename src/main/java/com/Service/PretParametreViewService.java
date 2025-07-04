package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Repository.PretRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PretParametreViewService {
    @Autowired
    private PretParametreViewRepository pretParametreViewRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdherentQuotaService adherentQuotaService;

    public List<PretParametreView> getAllPretOrderByDateFinAscByIdInscription(Long idInscription) {
        return this.pretParametreViewRepository.getAllPretOrderByDateFinAscByIdInscription(idInscription);
    }

    public PretParametreView findPretWhereExemplaireIn(LocalDateTime dateCible, Long idExemplaire) {
        return this.pretParametreViewRepository.findPretWhereExemplaireIn(dateCible, idExemplaire);
    }

    public Integer getQuotaDepenseActuel(LocalDateTime dateCible, Long idInscription) {
        return this.pretParametreViewRepository.getQuotaDepenseActuel(dateCible, idInscription);
    }

}
