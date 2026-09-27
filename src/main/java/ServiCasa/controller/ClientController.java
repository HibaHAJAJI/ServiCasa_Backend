package ServiCasa.controller;

import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ClientResponseDTO createClient(@Valid @RequestBody ClientRequestDTO dto){
        return clientService.addClient(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ClientResponseDTO updateClient(@Valid @RequestBody ClientUpdateRequestDTO dto, @PathVariable Long id){
        return clientService.updateClient(dto,id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ClientResponseDTO> getAllClients(Pageable pageable){
        return clientService.findAllClients(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ClientResponseDTO getById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteClientById(@PathVariable Long id){
         clientService.deleteClient(id);
    }
}
