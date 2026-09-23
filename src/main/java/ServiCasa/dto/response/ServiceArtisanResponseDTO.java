package ServiCasa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceArtisanResponseDTO {

    private Long id;

    private String nom;

    private String description;

    private BigDecimal tarif;

    private Long artisanId;

    private Long categorieId;

    private String categorieNom;
}