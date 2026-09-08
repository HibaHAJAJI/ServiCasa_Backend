package ServiCasa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ClientRequestDTO  extends UserRegisterRequest {

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;

}
