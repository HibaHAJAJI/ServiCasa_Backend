package ServiCasa.service;


import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DisponibiliteService {

    DisponibiliteResponseDTO addDisponibilite(DisponibiliteRequestDTO dto);

    Page<DisponibiliteResponseDTO> findAllDisponibilites(Pageable pageable);

    DisponibiliteResponseDTO findDisponibiliteById(Long id);

    Page<DisponibiliteResponseDTO> findByArtisanId(Long artisanId,Pageable pageable);

    DisponibiliteResponseDTO updateDisponibilite(DisponibiliteRequestDTO dto, Long id);

    void deleteDisponibilite(Long id);

}
