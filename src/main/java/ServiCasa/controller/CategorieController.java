package ServiCasa.controller;

import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;
import ServiCasa.service.CategorieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {


    private final CategorieService categorieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategorieResponseDTO createCategorie(@Valid @RequestBody CategorieRequestDTO dto){
        return categorieService.addCategorie(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategorieById(@PathVariable Long id){
        categorieService.deleteCategorie(id);
    }
}
