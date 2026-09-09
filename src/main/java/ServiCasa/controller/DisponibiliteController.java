package ServiCasa.controller;

import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import ServiCasa.service.DisponibiliteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/disponibilites")
@RequiredArgsConstructor
public class DisponibiliteController {


    private final DisponibiliteService disponibiliteService;

    @PostMapping
    public DisponibiliteResponseDTO createDisponibilite(@Valid @RequestBody DisponibiliteRequestDTO dto){
        return disponibiliteService.addDisponibilite(dto);
    }

    @PutMapping("/{id}")
    public DisponibiliteResponseDTO updateDisponibilite(@Valid @RequestBody DisponibiliteRequestDTO dto, @PathVariable Long id){
        return disponibiliteService.updateDisponibilite(dto,id);
    }

    @GetMapping
    public Page<DisponibiliteResponseDTO> getAllDisponibilites(Pageable pageable){
        return disponibiliteService.findAllDisponibilites(pageable);
    }

    @GetMapping("/{id}")
    public DisponibiliteResponseDTO getDisponibiliteByArtisan(@PathVariable Long id){
        return disponibiliteService.findDisponibiliteByArtisan(id);
    }

    @DeleteMapping("/{id}")
    public void deleteDisponibiliteById(@PathVariable Long id){
        disponibiliteService.deleteDisponibilite(id);
    }


}
