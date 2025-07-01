DROP DATABASE IF EXISTS bibliotech;
CREATE DATABASE bibliotech;
USE bibliotech;

CREATE OR REPLACE TABLE categorie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50)
);

CREATE OR REPLACE TABLE film (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    duree DECIMAL(5,2)
);

CREATE OR REPLACE TABLE film_categorie (
    id_film BIGINT,
    id_categorie BIGINT,
    PRIMARY KEY (id_film, id_categorie),
    FOREIGN KEY (id_categorie) REFERENCES categorie(id) ON DELETE CASCADE,
    FOREIGN KEY (id_film) REFERENCES film(id) ON DELETE CASCADE
);  

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
DROP TABLE IF EXISTS type_employe;
DROP TABLE IF EXISTS jour_ferrie;

-- ============================================
-- CRÉATION DES TABLES
-- ============================================

-- Table Type d'employé
CREATE TABLE type_employe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Employé
CREATE OR REPLACE TABLE employe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    id_type_employe INT NOT NULL,
    date_embauche DATE NOT NULL,
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_type_employe) REFERENCES type_employe(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_employe_nom (nom),
    INDEX idx_employe_email (email)
);

-- Table Genre
CREATE TABLE genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Type d'adhérent
CREATE TABLE type_adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Type de prêt
CREATE TABLE type_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Livre
CREATE TABLE livre (
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
CREATE TABLE exemplaire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_livre INT NOT NULL,
    reference VARCHAR(50) NOT NULL UNIQUE,
    date_arrivee DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_exemplaire_reference (reference)
);

-- Table Inscription
CREATE TABLE inscription (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_inscription DATE NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL,
    telephone VARCHAR(20),
    email VARCHAR(100),
    id_type_adherent INT NOT NULL,
    duree_mois INT NOT NULL,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE KEY unique_nom_prenom (nom, prenom),
    INDEX idx_inscription_nom (nom)
);

-- Table Paramètres de prêt (règles de gestion)
CREATE TABLE parametre_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_adherent INT NOT NULL,
    id_type_pret INT NOT NULL,
    id_genre INT NOT NULL,
    nb_jour_pret INT NOT NULL,
    nb_livre_pretable_en_meme_temps INT NOT NULL,
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
CREATE TABLE pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_inscription INT NOT NULL,
    id_exemplaire INT NOT NULL,
    id_type_pret INT NOT NULL,
    date_pret DATE NOT NULL,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_inscription) REFERENCES inscription(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_exemplaire) REFERENCES exemplaire(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_pret_dates (date_pret)
);

-- Table Remise de livre
CREATE TABLE remise_livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pret INT NOT NULL,
    date_remise DATE NOT NULL,
    commentaire TEXT,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Table Réservation
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_inscription INT NOT NULL,
    id_livre INT NOT NULL,
    id_type_pret INT NOT NULL,
    date_reservation DATE NOT NULL,
    date_pret_souhaitee DATE NOT NULL,
    commentaire TEXT,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_inscription) REFERENCES inscription(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_reservation_dates (date_reservation, date_pret_souhaitee)
);

-- Table État réservation (historique)
CREATE TABLE etat_reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_reservation INT NOT NULL,
    date_changement DATE NOT NULL,
    etat ENUM('EN_ATTENTE', 'VALIDEE', 'REFUSEE', 'TRANSFORMEE_EN_PRET') NOT NULL,
    commentaire TEXT,
    id_employe INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_reservation) REFERENCES reservation(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES employe(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Table Prolongement de prêt
CREATE TABLE prolongement_pret (
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
CREATE TABLE validation_prolongement_pret (
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
CREATE TABLE jour_ferrie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    date_ferrie DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_jour_ferrie_date (date_ferrie)
);

-- ============================================
-- INSERTION DES DONNÉES DE TEST
-- ============================================
