package ServiCasa.dto.response;


import ServiCasa.entity.Ville;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ServiCasa.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String nom;

    private String prenom;

    private String telephone;

    private Ville ville;

    private String email;

    private Role role;
}
