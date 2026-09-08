package ServiCasa.controller;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public List<ReservationResponseDTO> getAllReservations(){
        return reservationService.findAllReservations();
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationByArtisan(@PathVariable Long id){
        return reservationService.findReservationById(id);
    }


}
