ALTER TABLE avis
    DROP FOREIGN KEY fk_avis_reservations;

ALTER TABLE avis
    DROP COLUMN reservation_id;
