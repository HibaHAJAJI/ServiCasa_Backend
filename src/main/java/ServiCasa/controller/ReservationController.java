package ServiCasa.controller;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ReservationResponseDTO createReservation(@Valid @RequestBody ReservationRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.addReservation(dto, email);
    }

    @GetMapping("/client")
    @PreAuthorize("hasRole('CLIENT')")
    public Page<ReservationResponseDTO> getMyReservations(Authentication authentication,Pageable pageable) {
        String email = authentication.getName();
        return reservationService.getMyReservations(email,pageable);
    }

    @GetMapping("/artisan/demandes")
    @PreAuthorize("hasRole('ARTISAN')")
    public Page<ReservationResponseDTO> getPendingReservations(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getPendingReservationsByArtisan(email, pageable);
    }

    @GetMapping("/{id}")
    public ReservationResponseDTO getReservationById(@PathVariable Long id) {
        return reservationService.findReservationById(id);
    }

    @PatchMapping("/{id}/accepter")
    @PreAuthorize("hasRole('ARTISAN')")
    public ReservationResponseDTO accepter(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.accepterReservation(id, email);
    }

    @PatchMapping("/{id}/refuser")
    @PreAuthorize("hasRole('ARTISAN')")
    public ReservationResponseDTO refuser(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.refuserReservation(id, email);
    }

    @PatchMapping("/{id}/terminer")
    @PreAuthorize("hasRole('ARTISAN')")
    public ReservationResponseDTO terminer(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return reservationService.terminerReservation(id, email);
    }

    @DeleteMapping("/{id}/annuler")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ReservationResponseDTO updateReservation(@Valid @RequestBody ReservationRequestDTO dto, @PathVariable Long id) {
        return reservationService.updateReservation(id, dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReservationResponseDTO> getAllReservations(Pageable pageable) {
        return reservationService.findAllReservations(pageable);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReservationResponseDTO> getReservationsByClient(@PathVariable Long clientId, Pageable pageable) {
        return reservationService.findReservationsByClient(clientId, pageable);
    }

    @GetMapping("/latest")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ReservationResponseDTO> getLatestReservations(Pageable pageable) {
        return reservationService.getLatestReservations(pageable);
    }

    @GetMapping("/artisan/interventions")
    @PreAuthorize("hasRole('ARTISAN')")
    public Page<ReservationResponseDTO> getInterventions(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        return reservationService.getInterventionsByArtisan(email, pageable);
    }
}
