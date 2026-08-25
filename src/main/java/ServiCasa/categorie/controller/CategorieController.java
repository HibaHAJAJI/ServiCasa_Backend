package ServiCasa.categorie.controller;

import ServiCasa.categorie.dto.CategorieRequestDTO;
import ServiCasa.categorie.dto.CategorieResponseDTO;
import ServiCasa.categorie.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {


    private final CategorieService categorieService;

    @PostMapping
    public CategorieResponseDTO createCategorie(@RequestBody CategorieRequestDTO dto){
        return categorieService.addCategorie(dto);
    }

    @PutMapping("/{id}")
    public CategorieResponseDTO updateCategorie(@RequestBody CategorieRequestDTO dto, @PathVariable Long id){
        return categorieService.updateCategorie(id,dto);
    }

    @GetMapping
    public List<CategorieResponseDTO> getAllCategories(){
        return categorieService.findAllCategories();
    }


    @GetMapping("/{id}")
    public CategorieResponseDTO getById(@PathVariable Long id){
        return categorieService.findCategorieById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategorieById(@PathVariable Long id){
        categorieService.deleteCategorie(id);
    }
}
