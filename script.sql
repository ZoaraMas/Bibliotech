DROP DATABASE IF EXISTS bibliotech;
CREATE DATABASE bibliotech;
USE bibliotech;

-- ============================================
-- SCRIPT SQL BIBLIOTECH - GESTION BIBLIOTHEQUE
-- ============================================

-- Suppression des tables si elles existent (ordre inversé à cause des contraintes FK)
-- ============================================
-- SCRIPT SQL BIBLIOTECH - GESTION BIBLIOTHEQUE
-- ============================================

DROP TABLE IF EXISTS validation_prolongement_pret;
DROP TABLE IF EXISTS prolongement_pret;
DROP TABLE IF EXISTS etat_reservation;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS remise_livre;
DROP TABLE IF EXISTS pret;
DROP TABLE IF EXISTS inscription;
DROP TABLE IF EXISTS parametre_pret;
DROP TABLE IF EXISTS exemplaire;
DROP TABLE IF EXISTS livre;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS type_adherent;
DROP TABLE IF EXISTS type_pret;
DROP TABLE IF EXISTS employe;
DROP TABLE IF EXISTS type_user;
DROP TABLE IF EXISTS jour_ferrie;

-- ============================================
-- CRÉATION DES TABLES
-- ============================================

-- Table Type d'employé
CREATE OR REPLACE TABLE type_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Employé
CREATE OR REPLACE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    id_type_user INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_type_user) REFERENCES type_user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Table Genre
CREATE OR REPLACE TABLE genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Type d'adhérent
CREATE OR REPLACE TABLE type_adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Type de prêt
CREATE OR REPLACE TABLE type_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Livre
CREATE OR REPLACE TABLE livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    id_genre INT NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    edition VARCHAR(100),
    nb_page INT,
    resume TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_genre) REFERENCES genre(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_livre_titre (titre),
    INDEX idx_livre_auteur (auteur),
    INDEX idx_livre_isbn (isbn)
);

-- Table Exemplaire
CREATE OR REPLACE TABLE exemplaire (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_livre INT NOT NULL,
    reference VARCHAR(50) NOT NULL UNIQUE,
    date_arrivee DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_exemplaire_reference (reference)
);

-- Table Inscription
CREATE OR REPLACE TABLE inscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_inscription DATE NOT NULL,
    id_user BIGINT NOT NULL,
    id_type_adherent INT NOT NULL,
    duree_mois INT NOT NULL,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id),
    FOREIGN KEY (id_user) REFERENCES user(id),
    FOREIGN KEY (id_employe) REFERENCES user(id)
);

-- Table Paramètres de prêt (règles de gestion)
CREATE OR REPLACE TABLE parametre_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_adherent INT NOT NULL,
    id_type_pret INT NOT NULL,
    id_genre INT NOT NULL,
    nb_jour_pret INT NOT NULL,
    penalite_jours INT DEFAULT 0,
    nb_jours_avant_prolongation INT DEFAULT 3,
    nb_jours_prolongation INT DEFAULT 7,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_genre) REFERENCES genre(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY unique_parametre (id_type_adherent, id_type_pret, id_genre)
);


-- Table Prêt
CREATE OR REPLACE TABLE pret (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_inscription BIGINT NOT NULL,
    id_exemplaire BIGINT NOT NULL,
    id_type_pret INT NOT NULL,
    date_pret DATE NOT NULL,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_inscription) REFERENCES inscription(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_exemplaire) REFERENCES exemplaire(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_pret_dates (date_pret)
);

CREATE OR REPLACE TABLE remise_livre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pret BIGINT NOT NULL,
    date_remise DATE NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

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
        pp.penalite_jours,
        pp.nb_jours_avant_prolongation,
        pp.nb_jours_prolongation,
        pp.created_at AS pp_created_at, -- Aliased to avoid conflict with p.created_at
        DATE_ADD(p.date_pret, INTERVAL pp.nb_jour_pret DAY) AS date_fin_pret,
        COALESCE(rm.date_remise, NULL) AS date_remise
    FROM
        pret AS p
    JOIN
        inscription AS i ON i.id = p.id_inscription
    JOIN
        exemplaire AS e ON e.id = p.id_exemplaire
    JOIN
        livre AS l ON l.id = e.id_livre
    LEFT JOIN
        remise_livre AS rm ON p.id = rm.id_pret
    JOIN
        parametre_pret AS pp ON
            p.id_type_pret = pp.id_type_pret AND
            i.id_type_adherent = pp.id_type_adherent AND
            l.id_genre = pp.id_genre 
);

-- Table Paramètres de prêt (règles de gestion)
CREATE OR REPLACE TABLE adherent_quota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_adherent INT NOT NULL,
    quota INT NOT NULL,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Table Remise de livre


-- Table Réservation
CREATE OR REPLACE TABLE reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_inscription BIGINT NOT NULL,
    id_exemplaire BIGINT NOT NULL,
    id_type_pret INT NOT NULL,
    date_reservation DATE NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_inscription) REFERENCES inscription(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_exemplaire) REFERENCES exemplaire(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_reservation_dates (date_reservation)
);

-- Table État réservation (historique)
CREATE OR REPLACE TABLE etat_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_reservation BIGINT NOT NULL,
    date_changement DATE NOT NULL,
    etat ENUM('VALIDEE', 'REFUSEE') NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Checkpoint


-- Table Prolongement de prêt
CREATE OR REPLACE TABLE prolongement_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pret INT NOT NULL,
    date_demande DATE NOT NULL,
    commentaire TEXT,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Table État prolongement de prêt (historique)
CREATE OR REPLACE TABLE validation_prolongement_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_prolongement_pret INT NOT NULL,
    date_validation DATE NOT NULL,
    etat ENUM('EN_ATTENTE', 'VALIDEE', 'REFUSEE') NOT NULL,
    commentaire TEXT,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_prolongement_pret) REFERENCES prolongement_pret(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Table Jours fériés
CREATE OR REPLACE TABLE jour_ferrie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    date_ferrie DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_jour_ferrie_date (date_ferrie)
);

-- ============================================
-- INSERTION DES DONNÉES DE TEST
-- ============================================
