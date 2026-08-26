package ServiCasa.demandeService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeServiceResponseDTO {


    private Long id;

    private String nom;

    private String description;
}
