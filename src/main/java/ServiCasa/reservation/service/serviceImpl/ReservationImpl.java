package ServiCasa.reservation.service.serviceImpl;


import ServiCasa.reservation.mapper.ReservationMapper;
import ServiCasa.reservation.repository.ReservationRepository;
import ServiCasa.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;




}
