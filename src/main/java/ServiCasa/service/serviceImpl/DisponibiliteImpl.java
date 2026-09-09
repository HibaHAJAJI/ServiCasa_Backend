package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Disponibilite;
import ServiCasa.mapper.DisponibiliteMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.DisponibiliteRepository;
import ServiCasa.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class DisponibiliteImpl implements DisponibiliteService {

    private final DisponibiliteMapper mapper;
    private final DisponibiliteRepository repository;
    private final ArtisanRepository artisanRepository;

    @Override
   public DisponibiliteResponseDTO addDisponibilite(DisponibiliteRequestDTO dto){
       Artisan artisan= artisanRepository.findById(dto.getArtisanId())
               .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "artisan introuvable!"));

        Disponibilite disponibilite=mapper.toEntity(dto);
        disponibilite.setArtisan(artisan);
               return mapper.toDto(repository.save(disponibilite));

    }

    @Override
    public Page<DisponibiliteResponseDTO> findAllDisponibilites(Pageable pageable){
       return repository.findAll(pageable).map(mapper::toDto);

    }

    @Override
   public DisponibiliteResponseDTO findDisponibiliteByArtisan(Long artisanId){

        Disponibilite disponibilite = repository.findByArtisanId(artisanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune disponibilite pour cet artisan !"));
       return mapper.toDto(disponibilite);

   }

   @Override
  public DisponibiliteResponseDTO updateDisponibilite(DisponibiliteRequestDTO dto, Long id){
      Disponibilite disponibilite=repository.findById(id).orElseThrow(()
              ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponibilite introuvable !"));

      mapper.updateDisponibiliteDto(dto,disponibilite);

      Disponibilite update=repository.save(disponibilite);
      return mapper.toDto(update);
  }

  @Override
   public void deleteDisponibilite(Long id){
       if(!repository.existsById(id)){
          throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Disponiblite introuvable !");
       }
       repository.deleteById(id);
   }
}





