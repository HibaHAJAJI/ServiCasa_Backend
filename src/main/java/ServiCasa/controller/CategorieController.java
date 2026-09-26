package ServiCasa.controller;

import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;
import ServiCasa.service.CategorieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {


    private final CategorieService categorieService;

    @PostMapping
    public CategorieResponseDTO createCategorie(@Valid @RequestBody CategorieRequestDTO dto){
        return categorieService.addCategorie(dto);
    }

    @PutMapping("/{id}")
    public CategorieResponseDTO updateCategorie(@Valid@RequestBody CategorieRequestDTO dto, @PathVariable Long id){
        return categorieService.updateCategorie(id,dto);
    }

    @GetMapping
    public Page<CategorieResponseDTO> getAllCategories(Pageable pageable){
        return categorieService.findAllCategories(pageable);
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
