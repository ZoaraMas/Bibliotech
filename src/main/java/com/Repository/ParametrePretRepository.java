package com.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.AdherentQuota;
import com.Entite.Genre;
import com.Entite.Livre;
import com.Entite.ParametrePret;
import com.Entite.TypeAdherent;
import com.Entite.TypePret;
import com.Entite.User;

@Repository
public interface ParametrePretRepository extends JpaRepository<ParametrePret, Long> {
    Optional<ParametrePret> findByTypeAdherentAndTypePretAndGenre(TypeAdherent typeAdherent, TypePret typePret, Genre genre);
}
