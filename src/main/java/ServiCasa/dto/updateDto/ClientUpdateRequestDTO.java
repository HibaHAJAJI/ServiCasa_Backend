package ServiCasa.dto.updateDto;


import lombok.Data;

@Data
public class ClientUpdateRequestDTO {

    private String nom;

    private String prenom;

    private String telephone;

    private String ville;

    private String adresse;
}
