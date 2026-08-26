package ServiCasa.Disponibilite.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Data
public class DisponibiliteRequestDTO {

    private DayOfWeek jour;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private Boolean disponible;
}
