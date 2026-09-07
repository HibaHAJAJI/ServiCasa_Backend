package ServiCasa.service;


import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;

import java.util.List;

public interface CategorieService {

    CategorieResponseDTO addCategorie(CategorieRequestDTO dto);

    CategorieResponseDTO findCategorieById(Long id);

    List<CategorieResponseDTO> findAllCategories();

    CategorieResponseDTO updateCategorie(Long id, CategorieRequestDTO dto);

    void deleteCategorie(Long id);
}
