package ServiCasa.client.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ServiCasa.client.dto.ClientRequestDTO;
import ServiCasa.client.dto.ClientResponseDTO;
import ServiCasa.client.entity.Client;
import ServiCasa.client.mapper.ClientMapper;
import ServiCasa.client.repository.ClientRepository;
import ServiCasa.client.service.ClientService;
import ServiCasa.enums.Role;
import ServiCasa.user.repository.UserRepository;

import java.util.List;


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
    public List<ClientResponseDTO> findAllClients(){
        return mapper.toDtoList(repository.findAll());
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
