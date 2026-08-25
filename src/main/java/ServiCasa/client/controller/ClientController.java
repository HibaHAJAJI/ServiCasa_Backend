package ServiCasa.client.controller;

import ServiCasa.client.dto.ClientRequestDTO;
import ServiCasa.client.dto.ClientResponseDTO;
import ServiCasa.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ClientResponseDTO createClient(@RequestBody ClientRequestDTO dto){
        return clientService.addClient(dto);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO updateClient(@RequestBody ClientRequestDTO dto,@PathVariable Long id){
        return clientService.updateClient(dto,id);
    }

    @GetMapping
    public List<ClientResponseDTO> getAllClients(){
        return clientService.findAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponseDTO getById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id){
         clientService.deleteClient(id);
    }
}
