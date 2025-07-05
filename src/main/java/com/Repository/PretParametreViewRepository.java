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
public interface PretParametreViewRepository extends JpaRepository<PretParametreView, Long> {
        @Query("SELECT p FROM PretParametreView p WHERE p.idExemplaire = :idExemplaire AND p.dateRemise IS NULL ORDER BY p.datePret DESC")
        List<PretParametreView> findPretByExemplaireWhereDateRemiseIsNotNullOrderByDatePretDesc(
                        @Param("idExemplaire") Long idExemplaire);

        // obtenir tout les prets avec leurs parametres
        @Query(value = "SELECT * FROM pret_parametre WHERE id_inscription = :idInscription ORDER BY date_fin_pret ASC", nativeQuery = true)
        public List<PretParametreView> getAllPretOrderByDateFinAscByIdInscription(
                        @Param("idInscription") Long idInscription);

        @Query(value = "SELECT * FROM pret_parametre WHERE :dateCible BETWEEN date_pret AND date_fin_pret AND id_exemplaire = :idExemplaire", nativeQuery = true)
        public PretParametreView findPretWhereExemplaireIn(@Param("dateCible") LocalDateTime dateCible,
                        @Param("idExemplaire") Long idExemplaire);

        // on suppose ici que si date_remise n'est pas null, c'est que date_remise est
        // forcement avant aujourd'hui.
        @Query(value = "SELECT COUNT(*) FROM pret_parametre WHERE :dateCible BETWEEN date_pret AND date_fin_pret AND id_inscription = :idInscription AND date_remise is null", nativeQuery = true)
        public Integer getQuotaDepenseActuel(@Param("dateCible") LocalDateTime dateCible,
                        @Param("idInscription") Long idInscription);

}
