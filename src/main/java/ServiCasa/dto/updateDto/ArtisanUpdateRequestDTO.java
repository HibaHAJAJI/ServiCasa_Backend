package ServiCasa.dto.updateDto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArtisanUpdateRequestDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private String ville;

    private String specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String zoneIntervention;

    private String description;

}
