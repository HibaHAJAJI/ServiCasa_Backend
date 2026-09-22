package ServiCasa.service;

import ServiCasa.dto.request.AvisRequestDTO;
import ServiCasa.dto.response.AvisResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvisService {

    AvisResponseDTO addAvis(AvisRequestDTO dto, String clientEmail);

    Page<AvisResponseDTO> getAvisByArtisan(Long artisanId, Pageable pageable);

    Double getMoyenneArtisan(Long artisanId);

    Long getNombreAvisArtisan(Long artisanId);

    boolean existsByReservationId(Long reservationId);

    AvisResponseDTO getAvisByReservation(Long reservationId);
}