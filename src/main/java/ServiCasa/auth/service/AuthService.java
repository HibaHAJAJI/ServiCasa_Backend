package ServiCasa.auth.service;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.dto.request.ArtisanRequestDTO;


public interface AuthService {

    AuthResponseDTO login(AuthRequestDTO dto);

    AuthResponseDTO registerArtisan(ArtisanRequestDTO request);

}
