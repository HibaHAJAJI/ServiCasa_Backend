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



import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponseDTO createReservation(@Valid @RequestBody ReservationRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.addReservation(dto, email);
    }

    @GetMapping("/client")
    public Page<ReservationResponseDTO> getMyReservations(Authentication authentication,Pageable pageable) {
        String email = authentication.getName();
        return reservationService.getMyReservations(email,pageable);
    }

    @GetMapping("/artisan/demandes")
    public Page<ReservationResponseDTO> getPendingReservations(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getPendingReservationsByArtisan(email, pageable);
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationById(@PathVariable Long id) {
        return reservationService.findReservationById(id);
    }

    @PatchMapping("/{id}/accepter")
    public ReservationResponseDTO accepter(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.accepterReservation(id, email);
    }

    @PatchMapping("/{id}/refuser")
    public ReservationResponseDTO refuser(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.refuserReservation(id, email);
    }

    @PatchMapping("/{id}/terminer")
    public ReservationResponseDTO terminer(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.terminerReservation(id, email);
    }

    @DeleteMapping("/{id}/annuler")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ReservationResponseDTO updateReservation(@Valid @RequestBody ReservationRequestDTO dto, @PathVariable Long id) {
        return reservationService.updateReservation(id, dto);
    }

    @GetMapping
    public Page<ReservationResponseDTO> getAllReservations(Pageable pageable) {
        return reservationService.findAllReservations(pageable);
    }

    @GetMapping("/client/{clientId}")
    public Page<ReservationResponseDTO> getReservationsByClient(@PathVariable Long clientId, Pageable pageable) {
        return reservationService.findReservationsByClient(clientId, pageable);
    }

    @GetMapping("/latest")
    public Page<ReservationResponseDTO> getLatestReservations(Pageable pageable) {
        return reservationService.getLatestReservations(pageable);
    }

    @GetMapping("/artisan/interventions")
    public Page<ReservationResponseDTO> getInterventions(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getInterventionsByArtisan(email, pageable);
    }
}
