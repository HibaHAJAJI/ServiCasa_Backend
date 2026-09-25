package ServiCasa.service;

import ServiCasa.dto.request.PaiementRequestDTO;
import ServiCasa.dto.response.PaiementResponseDTO;

import java.util.List;

public interface PaiementService {

    List<PaiementResponseDTO> getAllPaiements();

    PaiementResponseDTO getPaiementById(Long id);

    PaiementResponseDTO getPaiementByReservationId(Long reservationId);

    PaiementResponseDTO createPaiement(PaiementRequestDTO dto, String email);

    PaiementResponseDTO updatePaiement(Long id, PaiementRequestDTO dto, String email);

    void deletePaiement(Long id, String email);

    long countPaiements();
}
