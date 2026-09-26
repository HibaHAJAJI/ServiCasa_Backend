package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;
import ServiCasa.entity.Categorie;
import ServiCasa.entity.DemandeService;
import ServiCasa.mapper.DemandeServiceMapper;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.repository.DemandeServiceRepository;
import ServiCasa.service.DemandeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandeServiceImpl implements DemandeServiceService {

    private final DemandeServiceMapper mapper;
    private final DemandeServiceRepository repository;
    private final CategorieRepository categorieRepository;


    @Override
    public DemandeServiceResponseDTO addDemandeService(DemandeServiceRequestDTO dto){
     Categorie categorie = categorieRepository.findById(dto.getCategorieId())
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie introuvable !"));
     DemandeService demandeService = mapper.toEntity(dto);
     demandeService.setCategorie(categorie);
       return mapper.toDto(repository.save(demandeService));
    }

    @Override
   public DemandeServiceResponseDTO findDemandeServiceById(Long id){
      DemandeService demandeService = repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande Service introuvable !"));
        return mapper.toDto(demandeService);
   }


   @Override
   public Page<DemandeServiceResponseDTO> findAllDemandeServices(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDto);
  }

@Override
    public DemandeServiceResponseDTO updateDemandeService(Long id, DemandeServiceRequestDTO dto){
        DemandeService demandeService= repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande Service introuvable !"));

        Categorie categorie = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Catégorie introuvable !"));

        mapper.updateDemandeServiceDto(dto,demandeService);
        demandeService.setCategorie(categorie);
        DemandeService update=repository.save(demandeService);

        return mapper.toDto(update);
    }

    @Override
   public void deleteDemandeService(Long id){
        if(!repository.existsById(id)){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande Service introuvable !");
        }
        repository.deleteById(id);
   }


}
