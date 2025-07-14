package com.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Entite.Prolongement;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Long> {
    public Optional<Prolongement> findByPretId(Long PretId);
}
