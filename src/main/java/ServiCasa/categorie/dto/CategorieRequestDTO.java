package ServiCasa.categorie.dto;

import ServiCasa.user.dto.UserRegisterRequest;
import lombok.Data;


@Data
public class CategorieRequestDTO extends UserRegisterRequest {

    private String nom;
}
