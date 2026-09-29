package ServiCasa.dto.response;


import ServiCasa.entity.Specialite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtisanResponseDTO extends UserResponse {

    private Specialite specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;

    private Double moyenneAvis;

    private Long nombreAvis;

}
