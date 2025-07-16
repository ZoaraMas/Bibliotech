package com.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.Inscription;
import com.Entite.TypeAdherent;
import com.Entite.User;

@Repository
public interface TypeAdherentRepository extends JpaRepository<TypeAdherent, Long> {
    // @Query(value = "SELECT * FROM ")
    Optional<TypeAdherent> findById(Integer id);
}
