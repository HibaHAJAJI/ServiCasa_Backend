package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;
import ServiCasa.entity.Categorie;
import ServiCasa.mapper.CategorieMapper;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.service.CategorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
   public Page<CategorieResponseDTO> findAllCategories(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDto);
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
