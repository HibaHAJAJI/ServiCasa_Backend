package ServiCasa.dto.response;

import ServiCasa.enums.ModePaiement;
import ServiCasa.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementResponseDTO {

    private Long id;

    private BigDecimal montant;

    private ModePaiement modePaiement;

    private StatutPaiement statutPaiement;

    private LocalDateTime datePaiement;

    private Long reservationId;
}