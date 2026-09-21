package ServiCasa.service;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReservationService {

    ReservationResponseDTO addReservation(ReservationRequestDTO dto);

    ReservationResponseDTO addReservation(ReservationRequestDTO dto, String clientEmail);

    ReservationResponseDTO findReservationById(Long id);

    Page<ReservationResponseDTO> findAllReservations(Pageable pageable);

    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto);

    ReservationResponseDTO updateReservationStatus(Long id, ServiCasa.enums.StatutReservation statut, String artisanEmail);

    Page<ReservationResponseDTO> findReservationsByClient(Long clientId, Pageable pageable);

    Page<ReservationResponseDTO> getPendingReservationsByArtisan(String email, Pageable pageable);

    Page<ReservationResponseDTO> getLatestReservations(Pageable pageable);

    java.util.List<ReservationResponseDTO> getMyReservations(String email);

    void cancelReservation(Long id);

}
