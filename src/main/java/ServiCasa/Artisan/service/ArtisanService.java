package ServiCasa.Artisan.service;


import ServiCasa.Artisan.dto.ArtisanRequestDTO;
import ServiCasa.Artisan.dto.ArtisanResponseDTO;

import java.util.List;

public interface ArtisanService {

    ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto);

    ArtisanResponseDTO findArtisanById(Long id);

    List<ArtisanResponseDTO> findAllArtisans();

    ArtisanResponseDTO updateArtisan(Long id, ArtisanRequestDTO dto);

    void deleteArtisan(Long id);
}
