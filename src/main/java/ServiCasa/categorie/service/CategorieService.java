package ServiCasa.categorie.service;


import ServiCasa.categorie.dto.CategorieRequestDTO;
import ServiCasa.categorie.dto.CategorieResponseDTO;

import java.util.List;

public interface CategorieService {

    CategorieResponseDTO addCategorie(CategorieRequestDTO dto);

    CategorieResponseDTO findCategorieById(Long id);

    List<CategorieResponseDTO> findAllCategories();

    CategorieResponseDTO updateCategorie(Long id, CategorieRequestDTO dto);

    void deleteCategorie(Long id);
}
