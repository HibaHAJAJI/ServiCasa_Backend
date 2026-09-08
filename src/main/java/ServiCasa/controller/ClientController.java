package ServiCasa.controller;

import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ClientResponseDTO createClient(@Valid @RequestBody ClientRequestDTO dto){
        return clientService.addClient(dto);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO updateClient(@Valid @RequestBody ClientRequestDTO dto,@PathVariable Long id){
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
