package ServiCasa.auth.service;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.request.UserRegisterRequest;


public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);

    AuthResponseDTO registerArtisan(ArtisanRequestDTO request);

    AuthResponseDTO registerClient(ClientRequestDTO request);

    AuthResponseDTO registerAdmin(UserRegisterRequest request);


}
