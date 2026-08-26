package ServiCasa.reservation.dto;

import ServiCasa.enums.StatutReservation;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ReservationRequestDTO {

    private LocalDateTime dateReservation;

    private LocalDateTime dateIntervention;

    private StatutReservation statutReservation;

    private String adressIntervention;

    private String descriptionProbleme;

    private BigDecimal prixTotal;

    private Long clientId;

    private Long artisanId;

    private Long demandeServiceId;


}
