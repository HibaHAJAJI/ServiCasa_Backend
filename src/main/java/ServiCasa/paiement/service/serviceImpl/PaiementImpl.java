package ServiCasa.paiement.service.serviceImpl;


import ServiCasa.paiement.mapper.PaiementMapper;
import ServiCasa.paiement.repository.PaiementRepository;
import ServiCasa.paiement.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaiementImpl implements PaiementService {

    private final PaiementMapper mapper;
    private final PaiementRepository repository;




}
