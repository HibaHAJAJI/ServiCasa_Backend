package ServiCasa.disponibilite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibiliteResponseDTO {

    private Long id;

    private DayOfWeek jour;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private Boolean disponible;

    private Long artisanId;;

}
