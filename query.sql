-- Verifier si un membre est inscrit
SELECT * 
FROM inscription
WHERE id_user = 2
AND DATE_ADD(date_inscription, INTERVAL duree_mois MONTH) > NOW();


-- Il faut encore obtenir le nombre de jour de pret etc
CREATE OR REPLACE VIEW pret_parametre AS (
    SELECT
        p.id,
        p.id_inscription,
        p.id_exemplaire,
        p.id_type_pret,
        p.date_pret,
        p.id_employe,
        p.created_at,
        pp.id AS pp_id, -- Aliased to avoid conflict with p.id
        pp.id_type_adherent,
        pp.id_type_pret AS pp_id_type_pret, -- Aliased to avoid conflict with p.id_type_pret
        pp.id_genre,
        pp.nb_jour_pret,
        pp.nb_livre_pretable_en_meme_temps,
        pp.penalite_jours,
        pp.nb_jours_avant_prolongation,
        pp.nb_jours_prolongation,
        pp.created_at AS pp_created_at, -- Aliased to avoid conflict with p.created_at
        DATE_ADD(p.date_pret, INTERVAL pp.nb_jour_pret DAY) AS date_fin_pret
    FROM
        pret AS p
    JOIN
        inscription AS i ON i.id = p.id_inscription
    JOIN
        exemplaire AS e ON e.id = p.id_exemplaire
    JOIN
        livre AS l ON l.id = e.id_livre
    JOIN
        parametre_pret AS pp ON
            p.id_type_pret = pp.id_type_pret AND
            i.id_type_adherent = pp.id_type_adherent AND
            l.id_genre = pp.id_genre
);

SELECT *
FROM pret_parametre
WHERE (date_pret <= '2024-06-15' AND date_fin_pret >= '2024-06-15')
AND id_exemplaire = 5;


WHERE (p.id_type_pret, i.id_type_adherent, l.id_genre) IN (
    SELECT pp.id_type_pret, pp.id_type_adherent, pp.id_genre
    FROM parametre_pret AS pp
)