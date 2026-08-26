CREATE TABLE users (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(255) NOT NULL,
                              prenom VARCHAR(255) NOT NULL,
                              telephone VARCHAR(50),
                              ville VARCHAR(100),
                              email VARCHAR(255) UNIQUE NOT NULL,
                              password VARCHAR(255) NOT NULL,
                              role VARCHAR(50) NOT NULL
);

CREATE TABLE clients (
                         id BIGINT PRIMARY KEY,
                         adresse VARCHAR(255)
);

CREATE TABLE artisans (
                          id BIGINT PRIMARY KEY,
                          annees_experience INT,
                          tarif_horaire DECIMAL(10, 2),
                          description TEXT,
                          zone_intervention VARCHAR(255),
                          specialite VARCHAR(255)
);


CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nom VARCHAR(255) NOT NULL
);

CREATE TABLE demande_services (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  nom VARCHAR(255) NOT NULL,
                                  description TEXT,
                                  categorie_id BIGINT
);

CREATE TABLE disponibilites (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                jour VARCHAR(50),
                                heure_debut TIME,
                                heure_fin TIME,
                                disponible BOOLEAN DEFAULT TRUE,
                                artisan_id BIGINT NOT NULL
);

CREATE TABLE reservations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              date_reservation DATETIME,
                              date_intervention DATETIME,
                              statut_reservation VARCHAR(50),
                              adress_intervention VARCHAR(255),
                              description_probleme TEXT,
                              prix_total DECIMAL(10, 2),
                              client_id BIGINT NOT NULL,
                              artisan_id BIGINT NOT NULL,
                              demande_service_id BIGINT
);

CREATE TABLE paiements (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           montant DECIMAL(10, 2) NOT NULL,
                           statut_paiement VARCHAR(50) NOT NULL,
                           reservation_id BIGINT UNIQUE
);