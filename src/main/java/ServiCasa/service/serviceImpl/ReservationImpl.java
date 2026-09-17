package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.*;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.*;
import ServiCasa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final DemandeServiceRepository demandeServiceRepository;
    private final UserRepository userRepository;

    @Override
    public ReservationResponseDTO addReservation(ReservationRequestDTO dto){

        Artisan artisan= artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Artisan !"));
        Client client =clientRepository.findById(dto.getClientId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Client !"));
        DemandeService demandeService = demandeServiceRepository.findById(dto.getDemandeServiceId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun service demandé !"));

        Reservation reservation = mapper.toEntity(dto);
        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        return mapper.toDto(repository.save(reservation));

    }

   @Override
   public ReservationResponseDTO findReservationById(Long id){
        Reservation reservation =repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun reservation !"));
        return mapper.toDto(reservation);

    }

    @Override
    public Page<ReservationResponseDTO> findAllReservations(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto){
        Reservation reservation =repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun reservation !"));

        mapper.updateReservationDto(dto,reservation);

        Artisan artisan= artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Artisan !"));
        Client client =clientRepository.findById(dto.getClientId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Client !"));
        DemandeService demandeService = demandeServiceRepository.findById(dto.getDemandeServiceId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun service demandé !"));

        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        return  mapper.toDto(repository.save(reservation));

    }

    @Override
   public Page <ReservationResponseDTO> findReservationsByClient(Long clientId,Pageable pageable){
        Page<Reservation>reservations=repository.findByClientId(clientId,pageable);
        return reservations.map(mapper::toDto);
    }


    @Override
    public Page<ReservationResponseDTO> getPendingReservationsByArtisan(String email, Pageable pageable) {

        User user = userRepository.findByEmail(email).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        Page<Reservation> reservations = repository.findByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.EN_ATTENTE, pageable);

        return reservations.map(mapper::toDto);
    }



}
