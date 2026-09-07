package ServiCasa.service;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;

import java.util.List;

public interface ReservationService {

    ReservationResponseDTO addReservation(ReservationRequestDTO dto);

    ReservationResponseDTO findReservationById(Long id);

    List<ReservationResponseDTO> findAllReservations();

    ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto);


}
