package ServiCasa.service;


import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategorieService {

    CategorieResponseDTO addCategorie(CategorieRequestDTO dto);

    CategorieResponseDTO findCategorieById(Long id);

    Page<CategorieResponseDTO> findAllCategories(Pageable pageable);

    CategorieResponseDTO updateCategorie(Long id, CategorieRequestDTO dto);

    void deleteCategorie(Long id);
}
