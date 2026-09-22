package ServiCasa.controller;

import ServiCasa.dto.request.AvisRequestDTO;
import ServiCasa.dto.response.AvisResponseDTO;
import ServiCasa.service.AvisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @PostMapping
    public AvisResponseDTO createAvis(@Valid @RequestBody AvisRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return avisService.addAvis(dto, email);
    }

    @GetMapping("/artisan/{artisanId}")
    public Page<AvisResponseDTO> getAvisByArtisan(@PathVariable Long artisanId, Pageable pageable) {
        return avisService.getAvisByArtisan(artisanId, pageable);
    }

    @GetMapping("/artisan/{artisanId}/moyenne")
    public Double getMoyenneArtisan(@PathVariable Long artisanId) {
        return avisService.getMoyenneArtisan(artisanId);
    }

    @GetMapping("/artisan/{artisanId}/count")
    public Long getNombreAvisArtisan(@PathVariable Long artisanId) {
        return avisService.getNombreAvisArtisan(artisanId);
    }

    @GetMapping("/reservation/{reservationId}")
    public AvisResponseDTO getAvisByReservation(@PathVariable Long reservationId) {
        return avisService.getAvisByReservation(reservationId);
    }

    @GetMapping("/reservation/{reservationId}/exists")
    public boolean existsByReservationId(@PathVariable Long reservationId) {
        return avisService.existsByReservationId(reservationId);
    }
}