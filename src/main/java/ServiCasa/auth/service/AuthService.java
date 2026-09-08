package ServiCasa.auth.service;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);

}
