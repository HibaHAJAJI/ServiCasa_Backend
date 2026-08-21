package servicasa.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import servicasa.user.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String nom;

    private String prenom;

    private String telephone;

    private String ville;

    private String email;

    private String password;

    private Role role;
}
