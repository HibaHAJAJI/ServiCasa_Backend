package ServiCasa.controller;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {


    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponseDTO createReservation(@Valid @RequestBody ReservationRequestDTO dto){
        return reservationService.addReservation(dto);
    }

    @PutMapping("/{id}")
    public ReservationResponseDTO updateReservation(@Valid @RequestBody ReservationRequestDTO dto, @PathVariable Long id){
        return reservationService.updateReservation(id,dto);
    }

    @GetMapping
    public Page<ReservationResponseDTO> getAllReservations(Pageable pageable){
        return reservationService.findAllReservations(pageable);
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationByArtisan(@PathVariable Long id){
        return reservationService.findReservationById(id);
    }


}
