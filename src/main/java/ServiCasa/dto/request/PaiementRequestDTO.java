package ServiCasa.dto.request;

import ServiCasa.enums.StatutPaiement;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PaiementRequestDTO {

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;


    @NotNull(message = "La réservation est obligatoire")
    private Long reservationId;
}
