package ServiCasa.dto.updateDto;


import ServiCasa.entity.Specialite;
import ServiCasa.entity.Ville;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArtisanUpdateRequestDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private Ville ville;

    private Specialite specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String zoneIntervention;

    private String description;

}
