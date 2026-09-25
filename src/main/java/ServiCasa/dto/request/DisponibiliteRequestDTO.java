package ServiCasa.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class DisponibiliteRequestDTO {

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    @NotNull(message = "L'état de disponibilité est obligatoire")
    private Boolean disponible;

    @NotNull(message = "L'ID de l'artisan est obligatoire")
    private Long artisanId;

}
