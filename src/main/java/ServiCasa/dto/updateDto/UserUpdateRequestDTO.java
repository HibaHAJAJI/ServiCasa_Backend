package ServiCasa.dto.updateDto;


import ServiCasa.entity.Ville;
import lombok.Data;


@Data
public class UserUpdateRequestDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private Ville ville;



}
