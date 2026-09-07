package ServiCasa.service.serviceImpl;


import ServiCasa.mapper.PaiementMapper;
import ServiCasa.repository.PaiementRepository;
import ServiCasa.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaiementImpl implements PaiementService {

    private final PaiementMapper mapper;
    private final PaiementRepository repository;




}
