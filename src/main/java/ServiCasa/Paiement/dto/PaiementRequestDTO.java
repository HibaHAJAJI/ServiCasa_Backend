package ServiCasa.Paiement.dto;

import ServiCasa.enums.StatutPaiement;
import lombok.Data;



@Data
public class PaiementRequestDTO {

    private Double montant;

    private StatutPaiement statutPaiement;
}
