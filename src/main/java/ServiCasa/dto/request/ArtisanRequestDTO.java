package ServiCasa.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Data
public class ArtisanRequestDTO extends UserRegisterRequest {

    @NotBlank(message = "La spécialité est obligatoire")
    private String specialite;

    @Positive(message = "Les années d'expérience doivent être positives")
    private Integer anneesExperience;

    @NotNull(message = "Le tarif horaire est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le tarif horaire doit être supérieur à 0")
    private BigDecimal tarifHoraire;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotBlank(message = "La zone d'intervention est obligatoire")
    private String zoneIntervention;


}
