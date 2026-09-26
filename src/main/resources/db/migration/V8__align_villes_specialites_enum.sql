
CREATE TABLE IF NOT EXISTS villes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);

INSERT IGNORE INTO villes (nom) VALUES
('Casablanca'),('Rabat'),('Marrakech'),('Fès'),('Tanger'),('Agadir'),('Meknes'),('Oujda'),('Kenitra'),('Beni Mellal'),('El Jadida'),('Nador'),('Tetouan'),('Safi'),('Khouribga'),('Settat'),('Mohammedia'),('Essaouira'),('Ouarzazate'),('Errachidia');

CREATE TABLE IF NOT EXISTS specialites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE
);

INSERT IGNORE INTO specialites (nom) VALUES
('Plomberie'),('Électricité'),('Peinture'),('Climatisation'),('Menuiserie'),('Maçonnerie'),('Serrurerie'),('Jardinage'),('Nettoyage'),('Carrelage');

ALTER TABLE users ADD COLUMN ville_id BIGINT;
UPDATE users u JOIN villes v ON u.ville = v.nom SET u.ville_id = v.id;
ALTER TABLE users DROP COLUMN ville;
ALTER TABLE users ADD CONSTRAINT fk_users_villes FOREIGN KEY (ville_id) REFERENCES villes(id);

ALTER TABLE artisans ADD COLUMN specialite_id BIGINT;
UPDATE artisans a JOIN specialites s ON a.specialite = s.nom SET a.specialite_id = s.id;
ALTER TABLE artisans DROP COLUMN specialite;
ALTER TABLE artisans ADD CONSTRAINT fk_artisans_specialites FOREIGN KEY (specialite_id) REFERENCES specialites(id);

ALTER TABLE artisans MODIFY COLUMN statut_compte ENUM('EN_ATTENTE','ACCEPTE','REFUSE') NOT NULL DEFAULT 'EN_ATTENTE';
