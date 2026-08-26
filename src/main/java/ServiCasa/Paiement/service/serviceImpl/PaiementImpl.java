package ServiCasa.Paiement.service.serviceImpl;


import ServiCasa.Paiement.mapper.PaiementMapper;
import ServiCasa.Paiement.repository.PaiementRepository;
import ServiCasa.Paiement.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaiementImpl implements PaiementService {

    private final PaiementMapper mapper;
    private final PaiementRepository repository;




}
