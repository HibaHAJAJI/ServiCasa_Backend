package ServiCasa.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class ReservationRequestDTO {


    @NotNull(message = "La date d'intervention est obligatoire")
    private LocalDateTime dateIntervention;


    @NotBlank(message = "L'adresse d'intervention est obligatoire")
    private String adressIntervention;

    @NotBlank(message = "La description du problème est obligatoire")
    private String descriptionProbleme;

    private Long clientId;

    @NotNull(message = "L'artisan est obligatoire")
    private Long artisanId;

    private Long demandeServiceId;

    private BigDecimal prixTotal;


}
