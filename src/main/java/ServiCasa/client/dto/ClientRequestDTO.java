package ServiCasa.client.dto;

import lombok.Data;
import ServiCasa.user.dto.UserRegisterRequest;



@Data
public class ClientRequestDTO  extends UserRegisterRequest {

    private String adresse;


}
