package ServiCasa.controller;

import ServiCasa.dto.request.ServiceArtisanRequestDTO;
import ServiCasa.dto.response.ServiceArtisanResponseDTO;
import ServiCasa.service.ServiceArtisanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services-artisan")
@RequiredArgsConstructor
public class ServiceArtisanController {

    private final ServiceArtisanService serviceArtisanService;

    @PostMapping
    public ServiceArtisanResponseDTO createService(@Valid @RequestBody ServiceArtisanRequestDTO dto, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return serviceArtisanService.createService(dto, email);
    }

    @GetMapping("/artisan/{artisanId}")
    public Page<ServiceArtisanResponseDTO> getServicesByArtisan(@PathVariable Long artisanId,Pageable pageable) {
        return serviceArtisanService.getServicesByArtisan(artisanId,pageable);
    }

    @GetMapping("/mes-services")
    public Page<ServiceArtisanResponseDTO> getMyServices(Authentication authentication, Pageable pageable) {
        String email = authentication.getName();
        return serviceArtisanService.getServicesByArtisanEmail(email,pageable);
    }

    @GetMapping("/{id}")
    public ServiceArtisanResponseDTO getServiceById(@PathVariable Long id) {
        return serviceArtisanService.getServiceById(id);
    }

    @PutMapping("/{id}")
    public ServiceArtisanResponseDTO updateService(@Valid @RequestBody ServiceArtisanRequestDTO dto, @PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        return serviceArtisanService.updateService(id, dto, email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        serviceArtisanService.deleteService(id, email);
        return ResponseEntity.noContent().build();
    }
}