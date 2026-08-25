package ServiCasa.client.dto;

import lombok.Data;
import ServiCasa.user.dto.UserRegisterRequest;

import java.time.LocalDate;

@Data
public class ClientRequestDTO  extends UserRegisterRequest {
    private LocalDate dateNaissance;

}
