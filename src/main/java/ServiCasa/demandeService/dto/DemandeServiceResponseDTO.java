package ServiCasa.demandeService.dto;

import ServiCasa.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeServiceResponseDTO extends UserResponse {


    private Long id;

    private String nom;

    private String description;
}
