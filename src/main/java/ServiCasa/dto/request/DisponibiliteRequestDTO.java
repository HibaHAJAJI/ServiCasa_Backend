package ServiCasa.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Data
public class DisponibiliteRequestDTO {

    @NotNull(message = "Le jour est obligatoire")
    private DayOfWeek jour;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    @NotNull(message = "L'état de disponibilité est obligatoire")
    private Boolean disponibilite;

    @NotNull(message = "L'ID de l'artisan est obligatoire")
    private Long artisanId;

}
