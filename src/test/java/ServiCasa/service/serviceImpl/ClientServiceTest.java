package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.entity.Client;
import ServiCasa.entity.Ville;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.ClientService;
import ServiCasa.enums.Role;
import ServiCasa.service.serviceImpl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientMapper mapper;

    @Mock
    private ClientRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImpl service;

    @Test
    void shouldAddClient() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setNom("Hiba");
        request.setEmail("hiba@gmail.com");
        request.setTelephone("0600000000");

        Ville ville = new Ville();
        ville.setNom("Beni Mellal");
        request.setVille(ville);

        request.setPassword("password123");
        request.setAdresse("123 rue");

        Client client = new Client();
        client.setNom("Hiba");
        client.setEmail("hiba@gmail.com");
        client.setRole(Role.CLIENT);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(1L);
        response.setNom("Hiba");
        response.setEmail("hiba@gmail.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(client);
        when(repository.save(client)).thenReturn(client);
        when(mapper.toDto(client)).thenReturn(response);

        ClientResponseDTO result = service.addClient(request);

        assertEquals("Hiba", result.getNom());
        assertEquals("hiba@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowWhenAddClientEmailAlreadyExists() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setEmail("hiba@gmail.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.addClient(request));
    }

    @Test
    void shouldFindById() {
        Long id = 1L;

        Client client = new Client();
        client.setId(id);
        client.setNom("Hiba");

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(id);
        response.setNom("Hiba");

        when(repository.findById(id)).thenReturn(Optional.of(client));
        when(mapper.toDto(client)).thenReturn(response);

        ClientResponseDTO result = service.findById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hiba", result.getNom());
    }

    @Test
    void shouldThrowWhenFindByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findById(id));
    }

    @Test
    void shouldFindAllClients() {
        Client client = new Client();
        client.setId(1L);

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(client)));
        when(mapper.toDto(client)).thenReturn(response);

        Page<ClientResponseDTO> result = service.findAllClients(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldUpdateClient() {
        Long id = 1L;

        ClientRequestDTO request = new ClientRequestDTO();
        request.setNom("Hiba");
        request.setAdresse("456 rue");

        ClientUpdateRequestDTO dto = new ClientUpdateRequestDTO();
        dto.setNom("Hiba");
        dto.setAdresse("456 rue");

        Client client = new Client();
        client.setId(id);
        client.setNom("Hiba");
        client.setAdresse("123 rue");

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(id);
        response.setNom("Hiba");

        when(repository.findById(id)).thenReturn(Optional.of(client));
        when(mapper.toDto(client)).thenReturn(response);
        when(repository.save(client)).thenReturn(client);

        ClientResponseDTO result = service.updateClient(dto, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateClientNotFound() {
        Long id = 1L;

        ClientUpdateRequestDTO dto = new ClientUpdateRequestDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateClient(dto, id));
    }

    @Test
    void shouldDeleteById() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteClient(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteByIdNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.deleteClient(id));
    }
}
