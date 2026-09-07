package ServiCasa.service.serviceImpl;


import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;




}
