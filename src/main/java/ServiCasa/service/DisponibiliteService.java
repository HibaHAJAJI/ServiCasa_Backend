package ServiCasa.service;


import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;

import java.util.List;

public interface DisponibiliteService {

    DisponibiliteResponseDTO addDisponibilite(DisponibiliteRequestDTO dto);

    List<DisponibiliteResponseDTO> findAllDisponibilites();

    DisponibiliteResponseDTO findDisponibiliteByArtisan(Long artisanId);

    DisponibiliteResponseDTO updateDisponibilite(DisponibiliteRequestDTO dto, Long id);

    void deleteDisponibilite(Long id);

}
