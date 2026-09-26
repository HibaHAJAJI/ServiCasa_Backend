package ServiCasa.dto.request;

import ServiCasa.enums.ModePaiement;
import ServiCasa.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequestDTO {

    private Long reservationId;

    private ModePaiement modePaiement;

    private StatutPaiement statutPaiement;
}