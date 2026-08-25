package ServiCasa.artisan.dto;

import lombok.*;
import ServiCasa.user.dto.UserRegisterRequest;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class ArtisanRequestDTO extends UserRegisterRequest {

    private String specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;

    private LocalDate dateNaissance;
}
