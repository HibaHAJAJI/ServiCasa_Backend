package ServiCasa.service;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReservationService {

    ReservationResponseDTO addReservation(ReservationRequestDTO dto);

    ReservationResponseDTO findReservationById(Long id);

    Page<ReservationResponseDTO> findAllReservations(Pageable pageable);

    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto);


}
