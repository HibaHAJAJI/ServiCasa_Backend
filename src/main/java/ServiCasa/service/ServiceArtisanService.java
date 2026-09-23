package ServiCasa.service;

import ServiCasa.dto.request.ServiceArtisanRequestDTO;
import ServiCasa.dto.response.ServiceArtisanResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ServiceArtisanService {

    ServiceArtisanResponseDTO createService(ServiceArtisanRequestDTO dto, String artisanEmail);

    Page<ServiceArtisanResponseDTO> getServicesByArtisan(Long artisanId, Pageable pageable);

    Page<ServiceArtisanResponseDTO> getServicesByArtisanEmail(String artisanEmail,Pageable pageable);

    ServiceArtisanResponseDTO getServiceById(Long id);

    ServiceArtisanResponseDTO updateService(Long id, ServiceArtisanRequestDTO dto, String artisanEmail);

    void deleteService(Long id, String artisanEmail);
}