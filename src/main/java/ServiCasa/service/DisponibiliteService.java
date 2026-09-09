package ServiCasa.service;


import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DisponibiliteService {

    DisponibiliteResponseDTO addDisponibilite(DisponibiliteRequestDTO dto);

    Page<DisponibiliteResponseDTO> findAllDisponibilites(Pageable pageable);

    DisponibiliteResponseDTO findDisponibiliteByArtisan(Long artisanId);

    DisponibiliteResponseDTO updateDisponibilite(DisponibiliteRequestDTO dto, Long id);

    void deleteDisponibilite(Long id);

}
