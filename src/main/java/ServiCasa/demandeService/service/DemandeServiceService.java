package ServiCasa.demandeService.service;


import ServiCasa.demandeService.dto.DemandeServiceRequestDTO;
import ServiCasa.demandeService.dto.DemandeServiceResponseDTO;

import java.util.List;

public interface DemandeServiceService {

    DemandeServiceResponseDTO addDemandeService(DemandeServiceRequestDTO dto);

    DemandeServiceResponseDTO findDemandeServiceById(Long id);

    List<DemandeServiceResponseDTO> findAllDemandeServices();

    DemandeServiceResponseDTO updateDemandeService(Long id, DemandeServiceRequestDTO dto);

    void deleteDemandeService(Long id);
}
