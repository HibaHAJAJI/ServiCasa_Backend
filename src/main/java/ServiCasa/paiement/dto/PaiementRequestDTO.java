package ServiCasa.paiement.dto;

import ServiCasa.enums.StatutPaiement;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaiementRequestDTO {

    private BigDecimal montant;

    private StatutPaiement statutPaiement;

    private Long reservationId;
}
