package ServiCasa.Artisan.controller;

import ServiCasa.Artisan.dto.ArtisanRequestDTO;
import ServiCasa.Artisan.dto.ArtisanResponseDTO;
import ServiCasa.Artisan.service.ArtisanService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artisans")
@RequiredArgsConstructor
public class ArtisanController {


    private final ArtisanService artisanService;

    @PostMapping
    public ArtisanResponseDTO createClient(@RequestBody ArtisanRequestDTO dto){
        return artisanService.addArtisan(dto);
    }

    @PutMapping("/{id}")
    public ArtisanResponseDTO updateClient(@RequestBody ArtisanRequestDTO dto,@PathVariable Long id){
        return artisanService.updateArtisan(id,dto);
    }

    @GetMapping
    public List<ArtisanResponseDTO> getAllClients(){
        return artisanService.findAllArtisans();
    }


    @GetMapping("/{id}")
    public ArtisanResponseDTO getById(@PathVariable Long id){
        return artisanService.findArtisanById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){
        artisanService.deleteArtisan(id);
    }
}
