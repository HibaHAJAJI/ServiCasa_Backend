package ServiCasa.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.entity.Client;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.repository.ClientRepository;
import ServiCasa.service.ClientService;
import ServiCasa.enums.Role;
import ServiCasa.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

   private final ClientMapper mapper;
   private final ClientRepository repository;
   private final UserRepository userRepository;


    @Override
    public ClientResponseDTO addClient(ClientRequestDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cet email est déjà utilisé !");
        }
        Client client= mapper.toEntity(dto);
        client.setRole(Role.CLIENT);
        return mapper.toDto(repository.save(client));
    }

    @Override
    public Page<ClientResponseDTO> findAllClients(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ClientResponseDTO findById(Long id){
        Client client=repository.findById(id).orElseThrow(()
                ->new RuntimeException("Client introvable !"));
       return mapper.toDto(client);
    }


    @Override
    public ClientResponseDTO updateClient(ClientRequestDTO dto, Long id){
        Client client=repository.findById(id).orElseThrow(()
                ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable !"));

        mapper.updateClientDto(dto,client);

        Client update=repository.save(client);
        return mapper.toDto(update);
   }

   @Override
   public void deleteClient(Long id){
        if(!repository.existsById(id)){
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable !");
        }
        repository.deleteById(id);
    }
}
