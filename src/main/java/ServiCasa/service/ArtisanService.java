package ServiCasa.service;


import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;

import java.util.List;

public interface ArtisanService {

    ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto);

    ArtisanResponseDTO findArtisanById(Long id);

    List<ArtisanResponseDTO> findAllArtisans();

    ArtisanResponseDTO updateArtisan(Long id, ArtisanRequestDTO dto);

    void deleteArtisan(Long id);
}
