package ServiCasa.Disponibilite.service.serviceImpl;


import ServiCasa.Disponibilite.mapper.DisponibiliteMapper;
import ServiCasa.Disponibilite.repository.DisponibiliteRepository;
import ServiCasa.Disponibilite.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DisponibiliteImpl implements DisponibiliteService {

    private final DisponibiliteMapper mapper;
    private final DisponibiliteRepository repository;




}
