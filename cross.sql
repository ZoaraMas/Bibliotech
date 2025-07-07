    -- ============================================
    -- SCRIPT D'INSERTION AUTOMATIQUE DES PARAMÈTRES DE PRÊT MANQUANTS
    -- ============================================

    -- Ce script va créer toutes les combinaisons manquantes de paramètres de prêt
    -- pour chaque type d'adhérent, type de prêt et genre existants

    INSERT INTO parametre_pret (id_type_adherent, id_type_pret, id_genre, nb_jour_pret, penalite_jours, nb_jours_avant_prolongation, nb_jours_prolongation)
    SELECT 
        ta.id as id_type_adherent,
        tp.id as id_type_pret,
        g.id as id_genre,
        -- Paramètres par défaut basés sur le type d'adhérent et le type de prêt
        CASE 
            -- Pour les prêts "Sur place" (type_pret = 2), toujours 1 jour
            WHEN tp.id = 2 THEN 1
            -- Pour les prêts "Domicile" (type_pret = 1), durée selon le type d'adhérent
            WHEN tp.id = 1 THEN
                CASE 
                    WHEN ta.id = 1 THEN 14  -- Etudiant: 14 jours
                    WHEN ta.id = 2 THEN 21  -- Professeur: 21 jours
                    WHEN ta.id = 3 THEN 14  -- Professionnel: 14 jours
                    WHEN ta.id = 4 THEN 7   -- Anonyme: 7 jours
                    ELSE 14  -- Défaut: 14 jours
                END
            ELSE 14  -- Défaut général: 14 jours
        END as nb_jour_pret,
        
        -- Pénalité par jour de retard selon le type d'adhérent et le type de prêt
        CASE 
            -- Pas de pénalité pour les prêts "Sur place"
            WHEN tp.id = 2 THEN 0
            -- Pour les prêts "Domicile", pénalité selon le type d'adhérent
            WHEN tp.id = 1 THEN
                CASE 
                    WHEN ta.id = 1 THEN 2  -- Etudiant: 2 jours de pénalité
                    WHEN ta.id = 2 THEN 1  -- Professeur: 1 jour de pénalité
                    WHEN ta.id = 3 THEN 2  -- Professionnel: 2 jours de pénalité
                    WHEN ta.id = 4 THEN 5  -- Anonyme: 5 jours de pénalité
                    ELSE 2  -- Défaut: 2 jours
                END
            ELSE 0  -- Défaut: pas de pénalité
        END as penalite_jours,
        
        -- Nombre de jours avant prolongation possible
        CASE 
            -- Pas de prolongation pour les prêts "Sur place"
            WHEN tp.id = 2 THEN 0
            -- Pour les prêts "Domicile", délai selon le type d'adhérent
            WHEN tp.id = 1 THEN
                CASE 
                    WHEN ta.id = 1 THEN 3  -- Etudiant: 3 jours avant
                    WHEN ta.id = 2 THEN 5  -- Professeur: 5 jours avant
                    WHEN ta.id = 3 THEN 3  -- Professionnel: 3 jours avant
                    WHEN ta.id = 4 THEN 2  -- Anonyme: 2 jours avant
                    ELSE 3  -- Défaut: 3 jours
                END
            ELSE 0  -- Défaut: pas de prolongation
        END as nb_jours_avant_prolongation,
        
        -- Nombre de jours de prolongation accordée
        CASE 
            -- Pas de prolongation pour les prêts "Sur place"
            WHEN tp.id = 2 THEN 0
            -- Pour les prêts "Domicile", durée selon le type d'adhérent
            WHEN tp.id = 1 THEN
                CASE 
                    WHEN ta.id = 1 THEN 7   -- Etudiant: 7 jours de prolongation
                    WHEN ta.id = 2 THEN 14  -- Professeur: 14 jours de prolongation
                    WHEN ta.id = 3 THEN 7   -- Professionnel: 7 jours de prolongation
                    WHEN ta.id = 4 THEN 3   -- Anonyme: 3 jours de prolongation
                    ELSE 7  -- Défaut: 7 jours
                END
            ELSE 0  -- Défaut: pas de prolongation
        END as nb_jours_prolongation

    FROM 
        type_adherent ta
        CROSS JOIN type_pret tp
        CROSS JOIN genre g
    WHERE 
        -- On exclut les combinaisons qui existent déjà
        NOT EXISTS (
            SELECT 1 
            FROM parametre_pret pp 
            WHERE pp.id_type_adherent = ta.id 
            AND pp.id_type_pret = tp.id 
            AND pp.id_genre = g.id
        )
    ORDER BY 
        ta.id, tp.id, g.id;

    -- Vérification du nombre de lignes insérées
    SELECT 
        COUNT(*) as total_parametres,
        COUNT(DISTINCT id_type_adherent) as nb_types_adherent,
        COUNT(DISTINCT id_type_pret) as nb_types_pret,
        COUNT(DISTINCT id_genre) as nb_genres
    FROM parametre_pret;

    -- Affichage des paramètres par type d'adhérent pour vérification
    SELECT 
        ta.libelle as type_adherent,
        tp.libelle as type_pret,
        g.nom as genre,
        pp.nb_jour_pret,
        pp.penalite_jours,
        pp.nb_jours_avant_prolongation,
        pp.nb_jours_prolongation
    FROM parametre_pret pp
    JOIN type_adherent ta ON pp.id_type_adherent = ta.id
    JOIN type_pret tp ON pp.id_type_pret = tp.id
    JOIN genre g ON pp.id_genre = g.id
    ORDER BY ta.libelle, tp.libelle, g.nom;