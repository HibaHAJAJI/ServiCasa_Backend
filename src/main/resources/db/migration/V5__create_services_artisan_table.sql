CREATE TABLE services_artisan (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  nom VARCHAR(255),
                                  description TEXT,
                                  tarif DECIMAL(10, 2),
                                  artisan_id BIGINT,
                                  categorie_id BIGINT,
                                  CONSTRAINT fk_services_artisan_artisans FOREIGN KEY (artisan_id) REFERENCES artisans (id) ON DELETE CASCADE,
                                  CONSTRAINT fk_services_artisan_categories FOREIGN KEY (categorie_id) REFERENCES categories (id) ON DELETE SET NULL
);
