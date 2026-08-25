package ServiCasa.categorie.service.serviceImpl;


import ServiCasa.categorie.dto.CategorieRequestDTO;
import ServiCasa.categorie.dto.CategorieResponseDTO;
import ServiCasa.categorie.entity.Categorie;
import ServiCasa.categorie.mapper.CategorieMapper;
import ServiCasa.categorie.repository.CategorieRepository;
import ServiCasa.categorie.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorieImpl implements CategorieService {

    private final CategorieMapper mapper;
    private final CategorieRepository repository;


    @Override
    public CategorieResponseDTO addCategorie(CategorieRequestDTO dto){
     Categorie categorie = mapper.toEntity(dto);
       return mapper.toDto(repository.save(categorie));
    }

    @Override
   public CategorieResponseDTO findCategorieById(Long id){
      Categorie categorie = repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Categorie introuvable !"));
        return mapper.toDto(categorie);
   }


    @Override
   public List<CategorieResponseDTO> findAllCategories(){
        return mapper.toDtoList(repository.findAll());
  }

  @Override
   public CategorieResponseDTO updateCategorie(Long id, CategorieRequestDTO dto){
        Categorie categorie= repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Categorie introuvable !"));

        mapper.updateCategorieDto(dto,categorie);
        Categorie update=repository.save(categorie);

        return mapper.toDto(update);
    }

    @Override
   public void deleteCategorie(Long id){
        if(!repository.existsById(id)){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Categorie introuvable !");
        }

        repository.deleteById(id);
   }


}
