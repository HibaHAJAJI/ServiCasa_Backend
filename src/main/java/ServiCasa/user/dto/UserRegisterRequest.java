package ServiCasa.user.dto;

import lombok.Data;
import ServiCasa.enums.Role;

@Data
public class UserRegisterRequest {

    private String nom;

    private String prenom;

    private String telephone;

    private String ville;

    private String email;

    private String password;

    private Role role;
}
