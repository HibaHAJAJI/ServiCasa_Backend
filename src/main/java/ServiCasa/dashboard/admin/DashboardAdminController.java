package ServiCasa.dashboard.admin;


import ServiCasa.entity.Artisan;
import ServiCasa.enums.StatutCompte;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/dashboard/admin")
@RequiredArgsConstructor
public class DashboardAdminController {

    private final DashboardAdminService dashboardAdminService;

        @GetMapping
        @PreAuthorize("hasRole('ADMIN')")
        public DashboardAdminResponseDTO getDashboard(Authentication authentication){
            String email = authentication.getName();
            return dashboardAdminService.getDashboardAdmin();
        }

        @GetMapping("/pending")
        @PreAuthorize("hasRole('ADMIN')")
        public Page<Artisan> getPendingArtisans(Pageable pageable) {

            return dashboardAdminService.getArtisansByStatus(StatutCompte.EN_ATTENTE, pageable);
        }

        @PutMapping("/{id}/status")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam StatutCompte statut) {
            dashboardAdminService.updateArtisanStatus(id, statut);
            return ResponseEntity.ok("Statut de l'artisan mis à jour avec succès !");
        }
}
