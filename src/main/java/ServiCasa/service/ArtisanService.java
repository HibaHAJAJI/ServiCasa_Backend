package ServiCasa.service;


import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArtisanService {

    ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto);

    ArtisanResponseDTO findArtisanById(Long id);

    Page<ArtisanResponseDTO> findAllArtisans(Pageable pageable);

    ArtisanResponseDTO updateArtisan(Long id, ArtisanRequestDTO dto);

    void deleteArtisan(Long id);
}
