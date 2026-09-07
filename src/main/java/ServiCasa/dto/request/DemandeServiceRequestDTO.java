package ServiCasa.dto.request;

import lombok.Data;



@Data
public class DemandeServiceRequestDTO  {

    private String nom;

    private String description;

    private Long categorieId;

}
