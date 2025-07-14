package com.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.SqlResultSetMapping;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
//  import org.springframework.data.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entite.Livre;
import com.Entite.Pret;
import com.Entite.PretParametreView;
import com.Entite.User;

@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {
    @Query(value = "SELECT p FROM Pret p JOIN FETCH p.inscription i JOIN FETCH i.user u WHERE p.id = :idPret")
    public Optional<Pret> findById(@Param("idPret") Long id);

    // obtenir tout les prets avec leurs parametres
    // @Query(value = "SELECT pp FROM pret_parametre pp WHERE id_inscription =
    // :idInscription ORDER BY date_fin_pret ASC", nativeQuery = true)
    // public List<PretParametreView> getAllPretOrderByDateFinAscByIdInscription(
    // @Param("idInscription") Integer idInscription);

    // @Query(value = "SELECT pp FROM pret_parametre pp WHERE :dateCible BETWEEN
    // date_pret AND date_fin_pret AND id_exemplaire = :idExemplaire", nativeQuery =
    // true)
    // public PretParametreView findPretWhereExemplaireIn(@Param("dateCible")
    // LocalDateTime dateCible,
    // @Param("idExemplaire") Long idExemplaire);

    // @Query(value = "SELECT COUNT(*) FROM pret_parametre WHERE :dateCible BETWEEN
    // date_pret AND date_fin_pret AND id_inscription = :idInscription", nativeQuery
    // = true)
    // public Integer getQuotaDepenseActuel(@Param("dateCible") LocalDateTime
    // dateCible,
    // @Param("idInscription") Integer idInscription);

}
