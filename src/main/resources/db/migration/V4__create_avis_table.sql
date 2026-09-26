CREATE TABLE avis (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      note INT,
                      commentaire TEXT,
                      date_creation DATETIME,
                      client_id BIGINT,
                      artisan_id BIGINT,
                      reservation_id BIGINT UNIQUE,
                      CONSTRAINT fk_avis_clients FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
                      CONSTRAINT fk_avis_artisans FOREIGN KEY (artisan_id) REFERENCES artisans (id) ON DELETE CASCADE,
                      CONSTRAINT fk_avis_reservations FOREIGN KEY (reservation_id) REFERENCES reservations (id) ON DELETE CASCADE
);
