ALTER TABLE paiements
    MODIFY COLUMN statut_paiement ENUM( 'EN_ATTENTE','PAYE','ANNULE','ECHOUE' ) NOT NULL,
    ADD COLUMN mode_paiement ENUM('CASH','CARTE','VIREMENT' ) AFTER montant,
    ADD COLUMN date_paiement DATETIME AFTER mode_paiement;