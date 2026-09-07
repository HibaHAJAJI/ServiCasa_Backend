package ServiCasa.dto.request;

import lombok.*;

import java.math.BigDecimal;


@Data
public class ArtisanRequestDTO extends UserRegisterRequest {

    private String specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;


}
