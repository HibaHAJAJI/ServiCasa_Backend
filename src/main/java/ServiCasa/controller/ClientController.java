package ServiCasa.controller;

import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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
    public Page<ClientResponseDTO> getAllClients(Pageable pageable){
        return clientService.findAllClients(pageable);
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
