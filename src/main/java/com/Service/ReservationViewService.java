package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Entite.Inscription;
import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.ReservationView;
import com.Entite.User;
import com.Entite.Livre;
import com.Repository.LivreRepository;
import com.Repository.PretParametreViewRepository;
import com.Repository.UserRepository;
import com.Repository.PretRepository;
import com.Repository.ReservationViewRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationViewService {
    @Autowired
    private ReservationViewRepository reservationViewRepository;
    // public boolean pretEstCouvertDansInscription(Inscription inscription, )

    public List<ReservationView> findAll() {
        return this.reservationViewRepository.findAll();
    }
}
