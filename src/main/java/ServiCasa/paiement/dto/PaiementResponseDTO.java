package ServiCasa.paiement.dto;

import ServiCasa.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementResponseDTO {

    private Long id;

    private BigDecimal montant;

    private StatutPaiement statutPaiement;

    private Long reservationId;
}
