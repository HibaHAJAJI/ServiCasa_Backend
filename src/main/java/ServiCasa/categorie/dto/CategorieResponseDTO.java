package ServiCasa.categorie.dto;

import ServiCasa.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorieResponseDTO extends UserResponse {

    private Long id;

    private String nom;
}
