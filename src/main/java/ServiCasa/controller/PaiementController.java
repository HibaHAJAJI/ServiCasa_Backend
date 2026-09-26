package ServiCasa.controller;

import ServiCasa.dto.request.PaiementRequestDTO;
import ServiCasa.dto.response.PaiementResponseDTO;
import ServiCasa.service.PaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> getAllPaiements() {
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countPaiements() {
        return ResponseEntity.ok(paiementService.countPaiements());
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<PaiementResponseDTO> getPaiementById(@PathVariable Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<PaiementResponseDTO> getPaiementByReservation(@PathVariable Long reservationId) {
        PaiementResponseDTO response = paiementService.getPaiementByReservationId(reservationId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PaiementResponseDTO> createPaiement(@Valid @RequestBody PaiementRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(paiementService.createPaiement(dto, email));
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<PaiementResponseDTO> updatePaiement(@PathVariable Long id, @Valid @RequestBody PaiementRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return ResponseEntity.ok(paiementService.updatePaiement(id, dto, email));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        paiementService.deletePaiement(id, email);
        return ResponseEntity.noContent().build();
    }
}
