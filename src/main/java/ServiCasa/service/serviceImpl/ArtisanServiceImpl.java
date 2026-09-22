package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.AvisRepository;
import ServiCasa.service.ArtisanService;
import ServiCasa.enums.Role;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ArtisanServiceImpl implements ArtisanService {

    private final ArtisanMapper mapper;
    private final ArtisanRepository repository;
    private final AvisRepository avisRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ArtisanResponseDTO addArtisan(ArtisanRequestDTO dto){

       if(userRepository.existsByEmail(dto.getEmail())){
           throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé !");
       }

       Artisan artisan = mapper.toEntity(dto);
       artisan.setPassword(passwordEncoder.encode(dto.getPassword()));
       artisan.setRole(Role.ARTISAN);

       return mapper.toDto(repository.save(artisan));
    }

    @Override
   public ArtisanResponseDTO findArtisanById(Long id){
        Artisan artisan = repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !"));
        ArtisanResponseDTO dto = mapper.toDto(artisan);
        Double moyenne = avisRepository.getAverageNoteByArtisan(artisan);
        Long nombre = avisRepository.countByArtisan(artisan);
        dto.setMoyenneAvis(moyenne != null ? moyenne : 0.0);
        dto.setNombreAvis(nombre != null ? nombre : 0L);
        return dto;

   }


   @Override
   public Page<ArtisanResponseDTO> findAllArtisans(Pageable pageable){
        return  repository.findAll(pageable).map(this::mapToDtoWithAvis);
  }

   public ArtisanResponseDTO updateArtisan(Long id, ArtisanUpdateRequestDTO dto){
        Artisan artisan= repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !"));

        mapper.updateArtisanDto(dto,artisan);
        Artisan update=repository.save(artisan);

        return mapper.toDto(update);
    }

    @Override
   public void deleteArtisan(Long id){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Artisan introuvable !");
        }
        repository.deleteById(id);
   }

   @Override
    public Page<ArtisanResponseDTO> findBySpecialiteArtisan(String specialite, Pageable pageable) {
       Page<Artisan> artisans = repository.findBySpecialite(specialite, pageable);
       return artisans.map(this::mapToDtoWithAvis);
   }

    @Override
    public Page<ArtisanResponseDTO>  findByVilleArtisan(String ville, Pageable pageable){
        Page<Artisan>artisans=repository.findByVille(ville,pageable);
        return artisans.map(this::mapToDtoWithAvis);
       }

   private ArtisanResponseDTO mapToDtoWithAvis(Artisan artisan) {
       ArtisanResponseDTO dto = mapper.toDto(artisan);
       Double moyenne = avisRepository.getAverageNoteByArtisan(artisan);
       Long nombre = avisRepository.countByArtisan(artisan);
       dto.setMoyenneAvis(moyenne != null ? moyenne : 0.0);
       dto.setNombreAvis(nombre != null ? nombre : 0L);
       return dto;
   }

}