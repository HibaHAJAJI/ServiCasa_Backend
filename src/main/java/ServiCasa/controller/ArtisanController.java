package ServiCasa.controller;

import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.entity.Specialite;
import ServiCasa.entity.Ville;
import ServiCasa.service.ArtisanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/artisans")
@RequiredArgsConstructor
public class ArtisanController {

    private final ArtisanService artisanService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ARTISAN')")
    public  ArtisanResponseDTO createArtisan(@Valid@RequestBody ArtisanRequestDTO dto){
        return artisanService.addArtisan(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ARTISAN')")
    public ArtisanResponseDTO updateArtisan(@Valid @RequestBody ArtisanUpdateRequestDTO dto, @PathVariable Long id){
        return artisanService.updateArtisan(id,dto);
    }

    @GetMapping
    public Page<ArtisanResponseDTO> getAllArtisans(Pageable pageable){
        return artisanService.findAllArtisans(pageable);
    }

    @GetMapping("/{id}")
    public ArtisanResponseDTO getById(@PathVariable Long id){
        return artisanService.findArtisanById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteArtisanById(@PathVariable Long id){
        artisanService.deleteArtisan(id);
    }


    @GetMapping("/specialite")
    public Page<ArtisanResponseDTO> getArtisanBySpecialite(String specialite, Pageable pageable){
        return artisanService.findBySpecialiteArtisan(specialite,pageable);
    }


    @GetMapping("/ville")
    public Page<ArtisanResponseDTO> getArtisanByVille(String ville,Pageable pageable) {
        return artisanService.findByVilleArtisan(ville, pageable);
    }

    @GetMapping("/search")
    public Page<ArtisanResponseDTO> findBySpecialiteAndVille(@RequestParam Ville ville, @RequestParam Specialite specialite, Pageable pageable) {
        return artisanService.findBySpecialiteAndVille(ville, specialite, pageable);
    }
}
