package ServiCasa.dto.response;


import ServiCasa.enums.SpecialiteArtisan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtisanResponseDTO extends UserResponse {

    private SpecialiteArtisan specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;

}
