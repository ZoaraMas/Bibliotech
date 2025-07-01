
-- Insertion des types d'employés
INSERT INTO type_user (libelle, description) VALUES
('Bibliothecaire', 'Bibliothécaire principal avec tous les droits'),
('Membre', 'Membre simple ayant acces a des fonctionalites limites');

-- Insertion des employés
INSERT INTO user (nom, prenom, date_naissance, email, password, telephone, id_type_user) VALUES
('Admin', 'Claire', '2000-01-01', 'admin', '', '0123456789', 1),
('membre', 'Sombre', '2000-01-01', 'membre', '', '0123456789', 2);
-- ('Leroy', 'Antoine', 'antoine.leroy', '','0234567890', 1, '2021-03-15', TRUE);
-- ('Garnier', 'Marie-Claire', 'mc.garnier@bibliotech.fr', '0345678901', 3, '2018-01-10', TRUE),
-- ('Roux', 'Thomas', 'thomas.roux@bibliotech.fr', '0456789012', 2, '2022-06-01', TRUE),
-- ('Simon', 'Julie', 'julie.simon@bibliotech.fr', '0567890123', 4, '2024-02-01', TRUE);

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
INSERT INTO parametre_pret (id_type_adherent, id_type_pret, id_genre, nb_jour_pret, nb_livre_pretable_en_meme_temps, penalite_jours, nb_jours_avant_prolongation, nb_jours_prolongation) VALUES
-- Étudiant
(1, 1, 1, 14, 3, 2, 3, 7),  -- Roman à domicile
(1, 1, 2, 14, 3, 2, 3, 7),  -- SF à domicile
(1, 1, 3, 21, 2, 3, 3, 10), -- Histoire à domicile
(1, 1, 4, 14, 2, 2, 3, 7),  -- Biographie à domicile
(1, 1, 5, 21, 2, 3, 3, 10), -- Informatique à domicile
(1, 1, 6, 14, 2, 2, 3, 7),  -- Philosophie à domicile
(1, 1, 7, 14, 2, 2, 3, 7),  -- Poésie à domicile
(1, 1, 8, 14, 2, 2, 3, 7),  -- Théâtre à domicile
(1, 1, 9, 14, 2, 2, 3, 7),  -- Essai à domicile
(1, 1, 10, 14, 3, 2, 3, 7), -- Jeunesse à domicile
(1, 2, 1, 1, 5, 0, 0, 0),   -- Roman sur place
(1, 2, 2, 1, 5, 0, 0, 0),   -- SF sur place
(1, 2, 3, 1, 3, 0, 0, 0),   -- Histoire sur place
(1, 2, 4, 1, 3, 0, 0, 0),   -- Biographie sur place
(1, 2, 5, 1, 3, 0, 0, 0),   -- Informatique sur place
-- Professeur
(2, 1, 1, 21, 5, 1, 5, 14), -- Roman à domicile
(2, 1, 2, 21, 5, 1, 5, 14), -- SF à domicile
(2, 1, 3, 30, 5, 2, 5, 14), -- Histoire à domicile
(2, 1, 4, 21, 5, 1, 5, 14), -- Biographie à domicile
(2, 1, 5, 30, 5, 2, 5, 14), -- Informatique à domicile
(2, 1, 6, 21, 5, 1, 5, 14), -- Philosophie à domicile
(2, 1, 7, 21, 5, 1, 5, 14), -- Poésie à domicile
(2, 1, 8, 21, 5, 1, 5, 14), -- Théâtre à domicile
(2, 1, 9, 21, 5, 1, 5, 14), -- Essai à domicile
(2, 1, 10, 21, 5, 1, 5, 14), -- Jeunesse à domicile
(2, 2, 1, 1, 10, 0, 0, 0),  -- Roman sur place
(2, 2, 2, 1, 10, 0, 0, 0),  -- SF sur place
(2, 2, 3, 1, 10, 0, 0, 0),  -- Histoire sur place
(2, 2, 4, 1, 10, 0, 0, 0),  -- Biographie sur place
(2, 2, 5, 1, 10, 0, 0, 0),  -- Informatique sur place
-- Professionnel
(3, 1, 1, 14, 3, 2, 3, 7),  -- Roman à domicile
(3, 1, 2, 14, 3, 2, 3, 7),  -- SF à domicile
(3, 1, 3, 21, 3, 3, 3, 10), -- Histoire à domicile
(3, 1, 4, 14, 3, 2, 3, 7),  -- Biographie à domicile
(3, 1, 5, 21, 3, 3, 3, 10), -- Informatique à domicile
(3, 1, 6, 14, 3, 2, 3, 7),  -- Philosophie à domicile
(3, 1, 7, 14, 3, 2, 3, 7),  -- Poésie à domicile
(3, 1, 8, 14, 3, 2, 3, 7),  -- Théâtre à domicile
(3, 1, 9, 14, 3, 2, 3, 7),  -- Essai à domicile
(3, 1, 10, 14, 3, 2, 3, 7), -- Jeunesse à domicile
(3, 2, 1, 1, 5, 0, 0, 0),   -- Roman sur place
(3, 2, 2, 1, 5, 0, 0, 0),   -- SF sur place
(3, 2, 3, 1, 5, 0, 0, 0),   -- Histoire sur place
(3, 2, 4, 1, 5, 0, 0, 0),   -- Biographie sur place
(3, 2, 5, 1, 5, 0, 0, 0),   -- Informatique sur place
-- Anonyme (restrictions plus importantes)
(4, 1, 1, 7, 1, 5, 2, 3),   -- Roman à domicile
(4, 1, 2, 7, 1, 5, 2, 3),   -- SF à domicile
(4, 1, 10, 7, 2, 5, 2, 3),  -- Jeunesse à domicile
(4, 2, 1, 1, 2, 0, 0, 0),   -- Roman sur place
(4, 2, 2, 1, 2, 0, 0, 0),   -- SF sur place
(4, 2, 10, 1, 3, 0, 0, 0);  -- Jeunesse sur place

-- Insertion des inscriptions
INSERT INTO inscription (date_inscription, nom, prenom, date_naissance, telephone, email, id_type_adherent, duree_mois, id_user) VALUES
('2024-01-15', 'Dupont', 'Marie', '1995-03-20', '0123456789', 'marie.dupont@email.com', 1, 12, 1),
('2024-01-20', 'Martin', 'Pierre', '1975-07-10', '0234567890', 'pierre.martin@email.com', 2, 12, 1),
('2024-02-01', 'Durand', 'Sophie', '1988-12-05', '0345678901', 'sophie.durand@email.com', 3, 6, 2),
('2024-02-10', 'Moreau', 'Jean', '2000-01-15', '0456789012', 'jean.moreau@email.com', 1, 12, 1),
('2024-02-15', 'Lefebvre', 'Anne', '1965-09-30', '0567890123', 'anne.lefebvre@email.com', 2, 12, 2),
('2024-03-01', 'Rousseau', 'Paul', '1992-04-25', '0678901234', 'paul.rousseau@email.com', 3, 3, 1),
('2024-03-10', 'Visitor', 'Anonymous', '1990-01-01', NULL, NULL, 4, 1, 4),
('2024-03-15', 'Petit', 'Lucas', '1998-11-12', '0789012345', 'lucas.petit@email.com', 1, 12, 2),
('2024-04-01', 'Bernard', 'Emma', '1980-06-18', '0890123456', 'emma.bernard@email.com', 2, 12, 1),
('2024-04-15', 'Thomas', 'Hugo', '1993-08-22', '0901234567', 'hugo.thomas@email.com', 3, 6, 2),
('2024-05-01', 'Blanc', 'Camille', '1997-02-14', '0912345678', 'camille.blanc@email.com', 1, 12, 1),
('2024-05-15', 'Girard', 'Nicolas', '1985-09-08', '0923456789', 'nicolas.girard@email.com', 3, 12, 2),
('2024-06-01', 'Faure', 'Léa', '1999-05-22', '0934567890', 'lea.faure@email.com', 1, 6, 1),
('2024-06-15', 'Laurent', 'Maxime', '1972-11-30', '0945678901', 'maxime.laurent@email.com', 2, 12, 2),
('2024-06-20', 'Morel', 'Clara', '1994-07-18', '0956789012', 'clara.morel@email.com', 3, 6, 1);

-- Insertion des prêts
INSERT INTO pret (id_inscription, id_exemplaire, id_type_pret, date_pret, id_user) VALUES
-- Prêts en cours
(1, 2, 1, '2024-06-15', 1),    -- Marie - Le Petit Prince
(2, 5, 1, '2024-06-10', 1),    -- Pierre - 1984
(4, 10, 1, '2024-06-20', 2),   -- Jean - Clean Code
(5, 16, 1, '2024-06-01', 2),   -- Anne - Harry Potter
(8, 17, 1, '2024-06-25', 1),   -- Lucas - Dune
(11, 19, 1, '2024-06-28', 2),  -- Camille - Les Misérables
(13, 21, 1, '2024-06-30', 1),  -- Léa - Introduction à l'algorithmique
-- Prêts rendus
(1, 7, 1, '2024-05-01', 1),    -- Marie - Steve Jobs (rendu)
(2, 12, 1, '2024-05-15', 2),   -- Pierre - Méditations (rendu)
(3, 1, 1, '2024-04-15', 1),    -- Sophie - Le Petit Prince (rendu)
(6, 15, 1, '2024-04-01', 2),   -- Paul - Harry Potter (rendu)
(9, 4, 1, '2024-05-20', 1),    -- Emma - 1984 (rendu)
(10, 18, 1, '2024-05-25', 2),  -- Hugo - Dune (rendu)
(12, 8, 1, '2024-06-05', 1),   -- Nicolas - Steve Jobs (rendu)
(14, 20, 1, '2024-06-10', 2);  -- Maxime - Introduction à l'algorithmique (rendu)

-- Insertion des remises de livre
INSERT INTO remise_livre (id_pret, date_remise, commentaire, id_user) VALUES
(8, '2024-05-14', 'Livre rendu en bon état', 1),
(9, '2024-06-05', 'Livre rendu à temps', 2),
(10, '2024-05-01', 'Livre rendu en avance', 1),
(11, '2024-04-20', 'Livre rendu avec quelques traces d\'usure', 2),
(12, '2024-06-10', 'Livre rendu en parfait état', 1),
(13, '2024-06-15', 'Livre rendu avec 5 jours de retard', 2),
(14, '2024-06-25', 'Livre rendu à temps', 1),
(15, '2024-06-28', 'Livre rendu en bon état', 2);

-- Insertion des réservations
INSERT INTO reservation (id_inscription, id_livre, id_type_pret, date_reservation, date_pret_souhaitee, commentaire, id_user) VALUES
(1, 4, 1, '2024-06-25', '2024-07-05', 'Réservation pour les vacances', 1),
(4, 2, 1, '2024-06-28', '2024-07-10', 'Besoin pour un projet étudiant', 2),
(9, 11, 1, '2024-06-20', '2024-07-01', 'Réservation confirmée', 1),
(13, 1, 1, '2024-06-30', '2024-07-15', 'Lecture d\'été', 2),
(15, 15, 1, '2024-07-01', '2024-07-20', 'Pour les vacances', 1),
(11, 6, 1, '2024-07-02', '2024-07-25', 'Lecture philosophique', 2);

-- Insertion des états de réservation
INSERT INTO etat_reservation (id_reservation,
