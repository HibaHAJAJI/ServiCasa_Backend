package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.service.ArtisanService;
import ServiCasa.enums.Role;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class ArtisanServiceImpl implements ArtisanService {

    private final ArtisanMapper mapper;
    private final ArtisanRepository repository;
    private final UserRepository userRepository;


    @Override
    public ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto){

       if(!userRepository.existsByEmail(dto.getEmail())){
           throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé !");
       }

       Artisan artisan = mapper.toEntity(dto);
       artisan.setRole(Role.ARTISAN);

       return mapper.toDto(repository.save(artisan));
    }

    @Override
   public ArtisanResponseDTO findArtisanById(Long id){
        Artisan artisan = repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !"));
        return mapper.toDto(artisan);

   }


   @Override
   public Page<ArtisanResponseDTO> findAllArtisans(Pageable pageable){
        return  repository.findAll(pageable).map(mapper::toDto);
  }

   public ArtisanResponseDTO updateArtisan(Long id, ArtisanRequestDTO dto){
        Artisan artisan= repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !"));

        mapper.updateArtisanDto(dto,artisan);
        Artisan update=repository.save(artisan);

        return mapper.toDto(update);
    }

    @Override
   public void deleteArtisan(Long id){
        if(!repository.existsById(id)){
            new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !");
        }
        repository.deleteById(id);
   }


}
