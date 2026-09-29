package ServiCasa.dto.updateDto;


import ServiCasa.entity.Ville;
import lombok.Data;

@Data
public class ClientUpdateRequestDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private Ville ville;

    private String adresse;
}
