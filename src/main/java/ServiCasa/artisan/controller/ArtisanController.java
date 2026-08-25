package ServiCasa.artisan.controller;

import ServiCasa.artisan.dto.ArtisanRequestDTO;
import ServiCasa.artisan.dto.ArtisanResponseDTO;
import ServiCasa.artisan.service.ArtisanService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artisans")
@RequiredArgsConstructor
public class ArtisanController {

    private final ArtisanService artisanService;

    @PostMapping
    public ArtisanResponseDTO createArtisan(@RequestBody ArtisanRequestDTO dto){
        return artisanService.addArtisan(dto);
    }

    @PutMapping("/{id}")
    public ArtisanResponseDTO updateArtisan(@RequestBody ArtisanRequestDTO dto,@PathVariable Long id){
        return artisanService.updateArtisan(id,dto);
    }

    @GetMapping
    public List<ArtisanResponseDTO> getAllArtisans(){
        return artisanService.findAllArtisans();
    }


    @GetMapping("/{id}")
    public ArtisanResponseDTO getById(@PathVariable Long id){
        return artisanService.findArtisanById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteArtisanById(@PathVariable Long id){
        artisanService.deleteArtisan(id);
    }
}
