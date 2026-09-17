package ServiCasa.service;


import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ArtisanService {

    ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto);

    ArtisanResponseDTO findArtisanById(Long id);

    Page<ArtisanResponseDTO> findAllArtisans(Pageable pageable);

    ArtisanResponseDTO updateArtisan(Long id, ArtisanUpdateRequestDTO dto);

    void deleteArtisan(Long id);

    Page<ArtisanResponseDTO> findBySpecialiteArtisan(String specialite, Pageable pageable);

    Page<ArtisanResponseDTO> findByVilleArtisan(String ville, Pageable pageable);


}
