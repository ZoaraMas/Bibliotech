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
    prolongement VARCHAR(20),
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
        p.prolongement,
        pp.id AS pp_id, -- Aliased to avoid conflict with p.id
        pp.id_type_adherent,
        pp.id_type_pret AS pp_id_type_pret, -- Aliased to avoid conflict with p.id_type_pret
        pp.id_genre,
        pp.nb_jour_pret,
        pp.penalite_jours,
        pp.nb_jours_avant_prolongation,
        pp.nb_jours_prolongation,
        pp.created_at AS pp_created_at, -- Aliased to avoid conflict with p.created_at
        CASE 
            WHEN p.prolongement is not null THEN DATE_ADD(p.date_pret, INTERVAL pp.nb_jours_prolongation DAY)
            WHEN p.id_type_pret = 2 THEN TIMESTAMP(DATE(p.date_pret), '20:00:00')
            ELSE DATE_ADD(p.date_pret, INTERVAL pp.nb_jour_pret DAY)
        END
            AS date_fin_pret,
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

CREATE OR REPLACE TABLE prolongement_pret (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pret BIGINT NOT NULL,
    date_demande DATE NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
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





CREATE OR REPLACE VIEW pret_nombre_desc AS (
    SELECT 
        l.id,
        l.titre,
        count(*) as nombre_pret
    FROM
        pret AS p
    JOIN
        exemplaire AS e ON e.id = p.id_exemplaire
    JOIN
        livre AS l ON l.id = e.id_livre
    GROUP BY l.id
    ORDER BY nombre_pret DESC
);

CREATE OR REPLACE VIEW user_count_pret_desc AS (
    SELECT 
        u.id,
        u.nom,
        count(*) as nombre_pret
    FROM
        pret AS p
    JOIN
        inscription AS i ON i.id = p.id_inscription
    JOIN 
        user AS u ON u.id = i.id_user
    GROUP BY u.id
    ORDER BY nombre_pret DESC
);

CREATE OR REPLACE VIEW reservation_view AS (
    SELECT
        r.id,
        r.id_inscription,
        r.id_exemplaire,
        r.id_type_pret,
        r.date_reservation,
        r.commentaire,
        r.id_employe
    FROM
        reservation AS r
);

CREATE OR REPLACE VIEW prolongement_pret_view AS (
    SELECT
        p.id,
        p.id_pret,
        p.date_demande,
        p.commentaire,
        p.id_employe
    FROM
        prolongement_pret AS p
);


-- Table État prolongement de prêt (historique)
CREATE OR REPLACE TABLE etat_prolongement_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_prolongement_pret BIGINT NOT NULL,
    date_validation DATE NOT NULL,
    etat ENUM('EN_ATTENTE', 'VALIDEE', 'REFUSEE') NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_prolongement_pret) REFERENCES prolongement_pret(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Pas encore utilise
CREATE OR REPLACE VIEW livre_exemplaires AS (
    SELECT
        l.id,
        l.titre,
        l.auteur,
        l.id_genre,
        l.isbn,
        l.edition,
        l.nb_page,
        l.resume
    FROM
        livre AS l
);









-- INSERTS




INSERT INTO type_user (libelle, description) VALUES
('Bibliothecaire', 'Bibliothécaire principal avec tous les droits'),
('Membre', 'Membre simple ayant acces a des fonctionalites limites');

-- Insertion des employés
INSERT INTO user (nom, prenom, date_naissance, email, password, telephone, id_type_user) VALUES
('Admin', 'Claire', '2000-01-01', 'admin', '', '0123456789', 1),
('membre', 'Sombre', '2000-01-01', 'membre', '', '0123456789', 2);

INSERT INTO user (nom, prenom, date_naissance, email, password, telephone, id_type_user) VALUES
('Martin', 'Pierre', '1975-07-10', 'pierre.martin@email.com', '', '0234567890', 2),
('Durand', 'Sophie', '1988-12-05', 'sophie.durand@email.com', '', '0345678901', 2),
('Moreau', 'Jean', '2000-01-15', 'jean.moreau@email.com', '', '0456789012', 2),
('Lefebvre', 'Anne', '1965-09-30', 'anne.lefebvre@email.com', '', '0567890123', 2),
('Rousseau', 'Paul', '1992-04-25', 'paul.rousseau@email.com', '', '0678901234', 2),
('Visitor', 'Anonymous', '1990-01-01', 'visitor@temp.com', '', '', 2),
('Petit', 'Lucas', '1998-11-12', 'lucas.petit@email.com', '', '0789012345', 2),
('Bernard', 'Emma', '1980-06-18', 'emma.bernard@email.com', '', '0890123456', 2),
('Thomas', 'Hugo', '1993-08-22', 'hugo.thomas@email.com', '', '0901234567', 2),
('Blanc', 'Camille', '1997-02-14', 'camille.blanc@email.com', '', '0912345678', 2),
('Girard', 'Nicolas', '1985-09-08', 'nicolas.girard@email.com', '', '0923456789', 2),
('Faure', 'Léa', '1999-05-22', 'lea.faure@email.com', '', '0934567890', 2),
('Laurent', 'Maxime', '1972-11-30', 'maxime.laurent@email.com', '', '0945678901', 2),
('Morel', 'Clara', '1994-07-18', 'clara.morel@email.com', '', '0956789012', 2);

-- Insertion des genres
INSERT INTO genre (nom) VALUES
('Roman'),
('Science-Fiction'),
('Histoire'),
('Biographie'),
('Informatique'),
('Philosophie'),
('Poésie'),
('Théâtre'),
('Essai'),
('Jeunesse');

-- Insertion des types d'adhérents
INSERT INTO type_adherent (libelle) VALUES
('Etudiant'),
('Professeur'),
('Professionnel'),
('Anonyme');

INSERT INTO adherent_quota (id_type_adherent, quota) VALUES
(1, 3),  -- Etudiant (assuming ID 1) can borrow 5 books
(2, 4), -- Professeur (assuming ID 2) can borrow 10 books
(3, 5),  -- Professionnel (assuming ID 3) can borrow 7 books
(4, 2);   -- Anonyme (assuming ID 4) can borrow 2 books

-- Insertion des types de prêt
INSERT INTO type_pret (libelle) VALUES
('Domicile'),
('Sur place');

-- Insertion des livres
INSERT INTO livre (titre, auteur, id_genre, isbn, edition, nb_page, resume) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', 1, '978-2070408504', 'Gallimard', 96, 'L\'histoire d\'un petit prince qui voyage de planète en planète'),
('1984', 'George Orwell', 2, '978-2070368228', 'Gallimard', 342, 'Un roman dystopique sur une société totalitaire'),
('Histoire de France', 'Jules Michelet', 3, '978-2253006169', 'Le Livre de Poche', 1200, 'Une histoire complète de la France'),
('Steve Jobs', 'Walter Isaacson', 4, '978-2709638326', 'JC Lattès', 656, 'La biographie officielle de Steve Jobs'),
('Clean Code', 'Robert C. Martin', 5, '978-0132350884', 'Prentice Hall', 464, 'Un manuel sur l\'écriture de code propre'),
('Méditations', 'Marc Aurèle', 6, '978-2080070739', 'Flammarion', 256, 'Réflexions philosophiques de l\'empereur romain'),
('Les Fleurs du Mal', 'Charles Baudelaire', 7, '978-2070417704', 'Gallimard', 352, 'Recueil de poèmes de Baudelaire'),
('Hamlet', 'William Shakespeare', 8, '978-2070411351', 'Gallimard', 256, 'La célèbre tragédie de Shakespeare'),
('L\'Art de la guerre', 'Sun Tzu', 9, '978-2081211483', 'Flammarion', 96, 'Traité de stratégie militaire'),
('Harry Potter à l\'école des sorciers', 'J.K. Rowling', 10, '978-2070518425', 'Gallimard Jeunesse', 320, 'Le premier tome de la saga Harry Potter'),
('Dune', 'Frank Herbert', 2, '978-2266320009', 'Pocket', 688, 'Un space opera épique sur la planète Arrakis'),
('Les Misérables', 'Victor Hugo', 1, '978-2253096337', 'Le Livre de Poche', 1664, 'L\'histoire de Jean Valjean dans la France du 19e siècle'),
('Introduction à l\'algorithmique', 'Thomas H. Cormen', 5, '978-2100545261', 'Dunod', 1312, 'Manuel de référence en algorithmique'),
('Sapiens', 'Yuval Noah Harari', 3, '978-2226393876', 'Albin Michel', 512, 'Une brève histoire de l\'humanité'),
('Le Seigneur des Anneaux', 'J.R.R. Tolkien', 1, '978-2266154093', 'Pocket', 1216, 'L\'épopée fantasy de la Terre du Milieu');

-- Insertion des exemplaires
INSERT INTO exemplaire (id_livre, reference, date_arrivee) VALUES
-- Le Petit Prince (3 exemplaires)
(1, 'EX001-PP-001', '2024-01-15'),
(1, 'EX001-PP-002', '2024-01-15'),
(1, 'EX001-PP-003', '2024-02-10'),
-- 1984 (2 exemplaires)
(2, 'EX002-1984-001', '2024-01-20'),
(2, 'EX002-1984-002', '2024-01-20'),
-- Histoire de France (1 exemplaire)
(3, 'EX003-HF-001', '2024-02-01'),
-- Steve Jobs (2 exemplaires)
(4, 'EX004-SJ-001', '2024-02-15'),
(4, 'EX004-SJ-002', '2024-02-15'),
-- Clean Code (3 exemplaires)
(5, 'EX005-CC-001', '2024-01-10'),
(5, 'EX005-CC-002', '2024-01-10'),
(5, 'EX005-CC-003', '2024-03-01'),
-- Autres livres (1-2 exemplaires chacun)
(6, 'EX006-MED-001', '2024-02-20'),
(7, 'EX007-FDM-001', '2024-02-25'),
(8, 'EX008-HAM-001', '2024-03-01'),
(9, 'EX009-ADG-001', '2024-03-05'),
(10, 'EX010-HP-001', '2024-03-10'),
(10, 'EX010-HP-002', '2024-03-10'),
(11, 'EX011-DUN-001', '2024-03-15'),
(11, 'EX011-DUN-002', '2024-03-15'),
(12, 'EX012-MIS-001', '2024-04-01'),
(13, 'EX013-ALG-001', '2024-04-05'),
(13, 'EX013-ALG-002', '2024-04-05'),
(14, 'EX014-SAP-001', '2024-04-10'),
(15, 'EX015-SDA-001', '2024-04-15'),
(15, 'EX015-SDA-002', '2024-04-15');

-- Insertion des paramètres de prêt
INSERT INTO parametre_pret (id_type_adherent, id_type_pret, id_genre, nb_jour_pret, penalite_jours, nb_jours_avant_prolongation, nb_jours_prolongation) VALUES
-- Étudiant
(1, 1, 1, 14, 2, 3, 7),  -- Roman à domicile
(1, 1, 2, 14, 2, 3, 7),  -- SF à domicile
(1, 1, 3, 21, 3, 3, 10), -- Histoire à domicile
(1, 1, 4, 14, 2, 3, 7),  -- Biographie à domicile
(1, 1, 5, 21, 3, 3, 10), -- Informatique à domicile
(1, 1, 6, 14, 2, 3, 7),  -- Philosophie à domicile
(1, 1, 7, 14, 2, 3, 7),  -- Poésie à domicile
(1, 1, 8, 14, 2, 3, 7),  -- Théâtre à domicile
(1, 1, 9, 14, 2, 3, 7),  -- Essai à domicile
(1, 1, 10, 14, 2, 3, 7), -- Jeunesse à domicile
(1, 2, 1, 1, 0, 0, 0),   -- Roman sur place
(1, 2, 2, 1, 0, 0, 0),   -- SF sur place
(1, 2, 3, 1, 0, 0, 0),   -- Histoire sur place
(1, 2, 4, 1, 0, 0, 0),   -- Biographie sur place
(1, 2, 5, 1, 0, 0, 0),   -- Informatique sur place
-- Professeur
(2, 1, 1, 21, 1, 5, 14), -- Roman à domicile
(2, 1, 2, 21, 1, 5, 14), -- SF à domicile
(2, 1, 3, 30, 2, 5, 14), -- Histoire à domicile
(2, 1, 4, 21, 1, 5, 14), -- Biographie à domicile
(2, 1, 5, 30, 2, 5, 14), -- Informatique à domicile
(2, 1, 6, 21, 1, 5, 14), -- Philosophie à domicile
(2, 1, 7, 21, 1, 5, 14), -- Poésie à domicile
(2, 1, 8, 21, 1, 5, 14), -- Théâtre à domicile
(2, 1, 9, 21, 1, 5, 14), -- Essai à domicile
(2, 1, 10, 21, 1, 5, 14), -- Jeunesse à domicile
(2, 2, 1, 1, 0, 0, 0),  -- Roman sur place
(2, 2, 2, 1, 0, 0, 0),  -- SF sur place
(2, 2, 3, 1, 0, 0, 0),  -- Histoire sur place
(2, 2, 4, 1, 0, 0, 0),  -- Biographie sur place
(2, 2, 5, 1, 0, 0, 0),  -- Informatique sur place
-- Professionnel
(3, 1, 1, 14, 2, 3, 7),  -- Roman à domicile
(3, 1, 2, 14, 2, 3, 7),  -- SF à domicile
(3, 1, 3, 21, 3, 3, 10), -- Histoire à domicile
(3, 1, 4, 14, 2, 3, 7),  -- Biographie à domicile
(3, 1, 5, 21, 3, 3, 10), -- Informatique à domicile
(3, 1, 6, 14, 2, 3, 7),  -- Philosophie à domicile
(3, 1, 7, 14, 2, 3, 7),  -- Poésie à domicile
(3, 1, 8, 14, 2, 3, 7),  -- Théâtre à domicile
(3, 1, 9, 14, 2, 3, 7),  -- Essai à domicile
(3, 1, 10, 14, 2, 3, 7), -- Jeunesse à domicile
(3, 2, 1, 1, 0, 0, 0),   -- Roman sur place
(3, 2, 2, 1, 0, 0, 0),   -- SF sur place
(3, 2, 3, 1, 0, 0, 0),   -- Histoire sur place
(3, 2, 4, 1, 0, 0, 0),   -- Biographie sur place
(3, 2, 5, 1, 0, 0, 0),   -- Informatique sur place
-- Anonyme (restrictions plus importantes)
(4, 1, 1, 7, 5, 2, 3),   -- Roman à domicile
(4, 1, 2, 7, 5, 2, 3),   -- SF à domicile
(4, 1, 10, 7, 5, 2, 3),  -- Jeunesse à domicile
(4, 2, 1, 1, 0, 0, 0),   -- Roman sur place
(4, 2, 2, 1, 0, 0, 0),   -- SF sur place
(4, 2, 10, 1, 0, 0, 0);  -- Jeunesse sur place

-- Inscription des membres (mises à jour pour 2025)
INSERT INTO inscription (date_inscription, id_user, id_type_adherent, duree_mois, id_employe) VALUES
('2024-12-15', 2, 1, 12, 1),  -- membre (id=2)
('2024-12-20', 3, 2, 12, 1),  -- Pierre Martin
('2025-01-01', 4, 3, 6, 1),   -- Sophie Durand
('2025-01-10', 5, 1, 12, 1),  -- Jean Moreau
('2025-01-15', 6, 2, 12, 1),  -- Anne Lefebvre
('2025-02-01', 7, 3, 3, 1),   -- Paul Rousseau
('2025-02-10', 8, 4, 1, 1),   -- Visitor Anonymous
('2025-02-15', 9, 1, 12, 1),  -- Lucas Petit
('2025-03-01', 10, 2, 12, 1), -- Emma Bernard
('2025-03-15', 11, 3, 6, 1),  -- Hugo Thomas
('2025-04-01', 12, 1, 12, 1), -- Camille Blanc
('2025-04-15', 13, 3, 12, 1), -- Nicolas Girard
('2025-05-01', 14, 1, 6, 1),  -- Léa Faure
('2025-05-15', 15, 2, 12, 1), -- Maxime Laurent
('2025-05-20', 16, 3, 6, 1),  -- Clara Morel
('2025-05-20', 1, 3, 6, 1);  -- Clara Morel

-- Insertion des prêts (juin et juillet 2025)
INSERT INTO pret (id_inscription, id_exemplaire, id_type_pret, date_pret, id_employe) VALUES
-- Prêts de juin 2025
(1, 1, 1, '2025-07-05', 1);    -- membre - Le Petit Prince (14 jours -> fin: 15/06)
-- (2, 4, 1, '2025-06-03', 1),    -- Pierre - 1984 (21 jours -> fin: 24/06)
-- (3, 7, 1, '2025-06-05', 2),    -- Sophie - Steve Jobs (14 jours -> fin: 19/06)
-- (4, 9, 1, '2025-06-08', 1),    -- Jean - Clean Code (21 jours -> fin: 29/06)
-- (5, 15, 1, '2025-06-10', 2),   -- Anne - Harry Potter (21 jours -> fin: 01/07)
-- (6, 12, 1, '2025-06-12', 1),   -- Paul - Méditations (14 jours -> fin: 26/06)
-- (7, 16, 1, '2025-06-15', 2),   -- Visitor - Harry Potter (7 jours -> fin: 22/06)
-- (8, 17, 1, '2025-06-18', 1),   -- Lucas - Dune (14 jours -> fin: 02/07)
-- (9, 20, 1, '2025-06-20', 2),   -- Emma - Introduction algo (21 jours -> fin: 11/07)
-- (10, 22, 1, '2025-06-22', 1),  -- Hugo - Sapiens (14 jours -> fin: 06/07)
-- (11, 2, 1, '2025-06-25', 2),   -- Camille - Le Petit Prince (14 jours -> fin: 09/07)
-- (12, 18, 1, '2025-06-27', 1),  -- Nicolas - Dune (14 jours -> fin: 11/07)
-- -- Prêts de juillet 2025
-- (13, 14, 1, '2025-07-01', 2),  -- Léa - Hamlet (14 jours -> fin: 15/07)
-- (14, 19, 1, '2025-07-03', 1),  -- Maxime - Les Misérables (21 jours -> fin: 24/07)
-- (15, 23, 1, '2025-07-05', 2),  -- Clara - Le Seigneur des Anneaux (14 jours -> fin: 19/07)
-- (1, 5, 1, '2025-07-10', 1),    -- membre - 1984 (14 jours -> fin: 24/07)
-- (2, 11, 1, '2025-07-12', 2),   -- Pierre - Méditations (21 jours -> fin: 02/08)
-- (3, 21, 1, '2025-07-15', 1),   -- Sophie - Introduction algo (14 jours -> fin: 29/07)
-- (4, 6, 1, '2025-07-18', 2),    -- Jean - Histoire de France (21 jours -> fin: 08/08)
-- (5, 24, 1, '2025-07-20', 1),   -- Anne - Le Seigneur des Anneaux (21 jours -> fin: 10/08)
-- (6, 8, 1, '2025-07-22', 2),    -- Paul - Steve Jobs (14 jours -> fin: 05/08)
-- (8, 3, 1, '2025-07-25', 1),    -- Lucas - Le Petit Prince (14 jours -> fin: 08/08)
-- (9, 13, 1, '2025-07-28', 2),   -- Emma - Les Fleurs du Mal (21 jours -> fin: 18/08)
-- (10, 10, 1, '2025-07-30', 1);  -- Hugo - Clean Code (14 jours -> fin: 13/08)


-- Insertion des remises de livre
-- INSERT INTO remise_livre (id_pret, date_remise, commentaire, id_employe) VALUES
-- -- Retours de juin 2025 (certains en avance, certains en retard)
-- (1, '2025-06-14', 'Retour en avance, livre en parfait état', 1),  -- Le Petit Prince - en avance (prévu 15/06)
-- (2, '2025-07-04', 'Retour avec 4 jours de retard', 2),           -- 1984 - en retard (prévu 24/06)
-- (3, '2025-06-18', 'Retour à temps', 1),                          -- Steve Jobs - à temps (prévu 19/06)
-- (4, '2025-06-27', 'Retour en avance de 2 jours', 2),             -- Clean Code - en avance (prévu 29/06)
-- (5, '2025-07-03', 'Retour avec 2 jours de retard', 1),           -- Harry Potter - en retard (prévu 01/07)
-- (6, '2025-06-25', 'Retour en avance', 2),                        -- Méditations - en avance (prévu 26/06)
-- (7, '2025-06-30', 'Retour avec 8 jours de retard, pénalité appliquée', 1), -- Visitor HP - très en retard (prévu 22/06)
-- (8, '2025-07-01', 'Retour en avance', 2),                        -- Dune - en avance (prévu 02/07)
-- -- Le prêt 9 (Emma - algo) n'est pas encore rendu (prévu 11/07)
-- (10, '2025-07-08', 'Retour avec 2 jours de retard', 1),          -- Hugo - Sapiens - en retard (prévu 06/07)
-- (11, '2025-07-07', 'Retour en avance', 2),                       -- Camille - Le Petit Prince - en avance (prévu 09/07)
-- -- Le prêt 12 (Nicolas - Dune) n'est pas encore rendu (prévu 11/07)

-- -- Retours de juillet 2025 (plus récents)
-- (13, '2025-07-14', 'Retour en avance', 1),                       -- Léa - Hamlet - en avance (prévu 15/07)
-- (14, '2025-07-26', 'Retour avec 2 jours de retard', 2),          -- Maxime - Les Misérables - en retard (prévu 24/07)
-- (15, '2025-07-18', 'Retour en avance', 1),                       -- Clara - LOTR - en avance (prévu 19/07)
-- (16, '2025-07-23', 'Retour en avance', 2),                       -- membre - 1984 - en avance (prévu 24/07)
-- -- Le prêt 17 (Pierre - Méditations) n'est pas encore rendu (prévu 02/08)
-- (18, '2025-07-30', 'Retour avec 1 jour de retard', 1),           -- Sophie - algo - en retard (prévu 29/07)
-- -- Les prêts 19-24 ne sont pas encore rendus (dates d'échéance futures)

-- -- Retours supplémentaires avec commentaires variés
-- (19, '2025-08-10', 'Retour avec 2 jours de retard, livre légèrement abîmé', 2), -- Jean - Histoire (prévu 08/08)
-- (20, '2025-08-08', 'Retour en avance', 1),                       -- Anne - LOTR - en avance (prévu 10/08)
-- (21, '2025-08-04', 'Retour en avance', 2),                       -- Paul - Steve Jobs - en avance (prévu 05/08)
-- (22, '2025-08-06', 'Retour en avance', 1),                       -- Lucas - Le Petit Prince - en avance (prévu 08/08)
-- -- Les prêts 23 et 24 restent non rendus pour simulation

-- -- Ajout de quelques retours avec des commentaires spéciaux
-- (23, '2025-08-20', 'Retour avec 2 jours de retard, pages cornées', 2),  -- Emma - Les Fleurs du Mal - en retard (prévu 18/08)
-- (24, '2025-08-11', 'Retour en avance, excellent état', 1); 
-- Checkpoint


-- Insertion des réservations
-- INSERT INTO reservation (id_inscription, id_livre, id_type_pret, date_reservation, date_pret_souhaitee, commentaire, id_user) VALUES
-- (1, 4, 1, '2024-06-25', '2024-07-05', 'Réservation pour les vacances', 1),
-- (4, 2, 1, '2024-06-28', '2024-07-10', 'Besoin pour un projet étudiant', 2),
-- (9, 11, 1, '2024-06-20', '2024-07-01', 'Réservation confirmée', 1),
-- (13, 1, 1, '2024-06-30', '2024-07-15', 'Lecture d\'été', 2),
-- (15, 15, 1, '2024-07-01', '2024-07-20', 'Pour les vacances', 1),
-- (11, 6, 1, '2024-07-02', '2024-07-25', 'Lecture philosophique', 2);

-- -- Insertion des états de réservation
-- INSERT INTO etat_reservation (id_reservation,



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
    