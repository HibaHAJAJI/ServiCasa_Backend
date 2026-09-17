package ServiCasa.controller;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/client/{clientId}")
    public Page <ReservationResponseDTO> getReservationsByClient(@PathVariable Long clientId,Pageable pageable){
        return reservationService.findReservationsByClient(clientId,pageable);
    }

    @GetMapping("/artisan/demandes")
    public Page<ReservationResponseDTO>getPendingReservations(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();

        return reservationService.getPendingReservationsByArtisan(email, pageable);
    }


}
