package ServiCasa.service;


import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DemandeServiceService {

    DemandeServiceResponseDTO addDemandeService(DemandeServiceRequestDTO dto);

    DemandeServiceResponseDTO findDemandeServiceById(Long id);

    Page<DemandeServiceResponseDTO> findAllDemandeServices(Pageable pageable);

    DemandeServiceResponseDTO updateDemandeService(Long id, DemandeServiceRequestDTO dto);

    void deleteDemandeService(Long id);
}
