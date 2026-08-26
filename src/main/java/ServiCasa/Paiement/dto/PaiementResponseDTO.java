package ServiCasa.Paiement.dto;

import ServiCasa.enums.StatutPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementResponseDTO {

    private Long id;

    private Double montant;

    private StatutPaiement statutPaiement;
}
