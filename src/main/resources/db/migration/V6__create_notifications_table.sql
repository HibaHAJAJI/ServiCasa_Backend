CREATE TABLE notifications (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               type ENUM('NOUVELLE_DEMANDE','DEMANDE_ACCEPTEE','DEMANDE_REFUSEE','RESERVATION_ANNULEE','INTERVENTION_TERMINEE','PAIEMENT_CONFIRME' ),
                               message VARCHAR(255),
                               date DATETIME,
                               reservation_id BIGINT,
                               user_id BIGINT,
                               CONSTRAINT fk_notifications_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

