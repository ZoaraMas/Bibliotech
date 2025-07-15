package com.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Entite.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.typeUser t WHERE u.id = :idUser")
    Optional<User> findById(@Param("idUser") Long idUser);

    // Option 1: Use regular JOINs (recommended)
    @Query("SELECT i.user FROM Inscription i JOIN i.user u JOIN u.typeUser t WHERE i.id = :idInscription")
    Optional<User> findByIdInscription(@Param("idInscription") Long idInscription);

    // Option 2: Alternative approach - select from User and join to Inscription
    // @Query("SELECT u FROM User u JOIN FETCH u.typeUser t JOIN u.inscriptions i
    // WHERE i.id = :idInscription")
    // Optional<User> findByIdInscription(@Param("idInscription") Long
    // idInscription);

    // Option 3: Simple approach without eager loading typeUser
    // @Query("SELECT i.user FROM Inscription i WHERE i.id = :idInscription")
    // Optional<User> findByIdInscription(@Param("idInscription") Long
    // idInscription);
}