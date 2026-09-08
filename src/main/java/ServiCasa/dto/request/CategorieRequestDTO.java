package ServiCasa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
public class CategorieRequestDTO  {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
}
