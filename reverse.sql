-- MySQL Workbench compatible script
-- Replace "CREATE OR REPLACE TABLE" with "CREATE TABLE"

CREATE TABLE type_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user (
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

CREATE TABLE genre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE type_adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE type_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

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

CREATE TABLE exemplaire (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_livre INT NOT NULL,
    reference VARCHAR(50) NOT NULL UNIQUE,
    date_arrivee DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_livre) REFERENCES livre(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_exemplaire_reference (reference)
);

CREATE TABLE inscription (
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

CREATE TABLE parametre_pret (
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

CREATE TABLE pret (
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

CREATE TABLE remise_livre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pret BIGINT NOT NULL,
    date_remise DATE NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE adherent_quota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_adherent INT NOT NULL,
    quota INT NOT NULL,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE reservation (
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

CREATE TABLE etat_reservation (
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

-- Missing Table 1: prolongement_pret
CREATE TABLE prolongement_pret (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pret BIGINT NOT NULL,
    date_demande DATE NOT NULL,
    commentaire TEXT,
    id_employe BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pret) REFERENCES pret(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (id_employe) REFERENCES user(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Missing Table 2: adherent_quota
-- (Although it was in your first "reverse" snippet, it was listed later in your full script)
CREATE TABLE adherent_quota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_adherent INT NOT NULL,
    quota INT NOT NULL,
    FOREIGN KEY (id_type_adherent) REFERENCES type_adherent(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Missing Table 3: etat_prolongement_pret
CREATE TABLE etat_prolongement_pret (
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

-- Note: Views are not included as they are not needed for schema diagram generation
-- The pret_parametre view can be added separately if needed