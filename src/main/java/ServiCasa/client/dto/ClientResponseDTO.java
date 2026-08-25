package ServiCasa.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ServiCasa.user.dto.UserResponse;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO extends UserResponse {
    private LocalDate dateNaissance;

}
