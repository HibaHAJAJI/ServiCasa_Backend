package ServiCasa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceArtisanRequestDTO {

    @NotBlank(message = "Le nom du service est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "Le tarif est obligatoire")
    private BigDecimal tarif;

    private Long categorieId;
}