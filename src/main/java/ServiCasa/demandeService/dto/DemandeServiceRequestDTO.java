package ServiCasa.demandeService.dto;

import ServiCasa.user.dto.UserRegisterRequest;
import lombok.Data;



@Data
public class DemandeServiceRequestDTO extends UserRegisterRequest {

    private String nom;

    private String description;
}
