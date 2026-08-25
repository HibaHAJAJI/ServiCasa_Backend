package ServiCasa.demandeService.service.serviceImpl;


import ServiCasa.demandeService.dto.DemandeServiceRequestDTO;
import ServiCasa.demandeService.dto.DemandeServiceResponseDTO;
import ServiCasa.demandeService.entity.DemandeService;
import ServiCasa.demandeService.mapper.DemandeServiceMapper;
import ServiCasa.demandeService.repository.DemandeServiceRepository;
import ServiCasa.demandeService.service.DemandeServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandeServiceImpl implements DemandeServiceService {

    private final DemandeServiceMapper mapper;
    private final DemandeServiceRepository repository;


    @Override
    public DemandeServiceResponseDTO addDemandeService(DemandeServiceRequestDTO dto){
     DemandeService demandeService = mapper.toEntity(dto);
       return mapper.toDto(repository.save(demandeService));
    }

    @Override
   public DemandeServiceResponseDTO findDemandeServiceById(Long id){
      DemandeService demandeService = repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande Service introuvable !"));
        return mapper.toDto(demandeService);
   }


    @Override
   public List<DemandeServiceResponseDTO> findAllDemandeServices(){
        return mapper.toDtoList(repository.findAll());
  }

  @Override
   public DemandeServiceResponseDTO updateDemandeService(Long id, DemandeServiceRequestDTO dto){
        DemandeService demandeService= repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Demande Service introuvable !"));

        mapper.updateDemandeServiceDto(dto,demandeService);
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
