ALTER TABLE clients
    ADD CONSTRAINT fk_clients_utilisateurs FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE artisans
    ADD CONSTRAINT fk_artisans_utilisateurs FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;


ALTER TABLE demande_services
    ADD CONSTRAINT fk_demande_services_categories FOREIGN KEY (categorie_id) REFERENCES categories (id) ON DELETE SET NULL;


ALTER TABLE disponibilites
    ADD CONSTRAINT fk_disponibilites_artisans FOREIGN KEY (artisan_id) REFERENCES artisans (id) ON DELETE CASCADE;


ALTER TABLE reservations
    ADD CONSTRAINT fk_reservations_clients FOREIGN KEY (client_id) REFERENCES clients (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_reservations_artisans FOREIGN KEY (artisan_id) REFERENCES artisans (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_reservations_services FOREIGN KEY (demande_service_id) REFERENCES demande_services (id) ON DELETE SET NULL;


ALTER TABLE paiements
    ADD CONSTRAINT fk_paiements_reservations FOREIGN KEY (reservation_id) REFERENCES reservations (id) ON DELETE CASCADE;