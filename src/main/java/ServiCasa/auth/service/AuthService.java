package ServiCasa.auth.service;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.dto.request.UserRegisterRequest;

public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);

    AuthResponseDTO register(UserRegisterRequest dto);


}
