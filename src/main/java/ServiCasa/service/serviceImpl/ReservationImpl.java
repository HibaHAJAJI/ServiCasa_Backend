package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.Reservation;
import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;

    @Override
    public ReservationResponseDTO addReservation(ReservationRequestDTO dto){

        Artisan artisan= artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Artisan !"));
        Client client =clientRepository.findById(dto.getClientId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun Client !"));

        Reservation reservation = mapper.toEntity(dto);
        reservation.setArtisan(artisan);
        reservation.setClient(client);

        return mapper.toDto(repository.save(reservation));

    }

   @Override
   public ReservationResponseDTO findReservationById(Long id){
        Reservation reservation =repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun reservation !"));
        return mapper.toDto(reservation);

    }

    @Override
    public List<ReservationResponseDTO> findAllReservations(){
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto){
        Reservation reservation =repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Aucun reservation !"));

        mapper.updateReservationDto(dto,reservation);
        return  mapper.toDto(repository.save(reservation));


    }





}
