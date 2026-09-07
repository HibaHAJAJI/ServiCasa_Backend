package ServiCasa.controller;

import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import ServiCasa.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/disponibilites")
@RequiredArgsConstructor
public class DisponibiliteController {


    private final DisponibiliteService disponibiliteService;

    @PostMapping
    public DisponibiliteResponseDTO createDisponibilite(@RequestBody DisponibiliteRequestDTO dto){
        return disponibiliteService.addDisponibilite(dto);
    }

    @PutMapping("/{id}")
    public DisponibiliteResponseDTO updateDisponibilite(@RequestBody DisponibiliteRequestDTO dto, @PathVariable Long id){
        return disponibiliteService.updateDisponibilite(dto,id);
    }

    @GetMapping
    public List<DisponibiliteResponseDTO> getAllDisponibilites(){
        return disponibiliteService.findAllDisponibilites();
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
