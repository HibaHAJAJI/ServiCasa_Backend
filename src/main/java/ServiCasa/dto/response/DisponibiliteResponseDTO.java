package ServiCasa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibiliteResponseDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureDebut;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureFin;

    private Boolean disponible;

    private Long artisanId;

}
