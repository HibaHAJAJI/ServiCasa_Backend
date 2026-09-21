package ServiCasa.dto.response;


import ServiCasa.enums.StatutReservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {

    private Long id;

    private LocalDateTime dateReservation;

    private LocalDateTime dateIntervention;

    private StatutReservation statutReservation;

    private String adressIntervention;

    private String descriptionProbleme;

    private BigDecimal prixTotal;

    private Long clientId;

    private String clientNom;

    private String clientPrenom;

    private Long artisanId;

    private String artisanNom;

    private String artisanPrenom;

    private String artisanSpecialite;

    private Long demandeServiceId;

    private Long paiementId;
}
