package ServiCasa.disponibilite.service.serviceImpl;


import ServiCasa.disponibilite.mapper.DisponibiliteMapper;
import ServiCasa.disponibilite.repository.DisponibiliteRepository;
import ServiCasa.disponibilite.service.DisponibiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DisponibiliteImpl implements DisponibiliteService {

    private final DisponibiliteMapper mapper;
    private final DisponibiliteRepository repository;




}
