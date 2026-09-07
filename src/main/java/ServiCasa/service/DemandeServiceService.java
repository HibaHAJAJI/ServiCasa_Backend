package ServiCasa.service;


import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;

import java.util.List;

public interface DemandeServiceService {

    DemandeServiceResponseDTO addDemandeService(DemandeServiceRequestDTO dto);

    DemandeServiceResponseDTO findDemandeServiceById(Long id);

    List<DemandeServiceResponseDTO> findAllDemandeServices();

    DemandeServiceResponseDTO updateDemandeService(Long id, DemandeServiceRequestDTO dto);

    void deleteDemandeService(Long id);
}
