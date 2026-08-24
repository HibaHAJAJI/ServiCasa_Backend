package servicasa.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import servicasa.user.enums.Role;

public class UserRegisterRequest {

    private String nom;

    private String prenom;

    private String telephone;

    private String ville;

    private String email;

    private String password;

    private Role role;
}
