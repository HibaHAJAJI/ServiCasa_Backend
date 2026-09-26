package ServiCasa.service;

import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.response.ClientResponseDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.dto.updateDto.ClientUpdateRequestDTO;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import ServiCasa.enums.Role;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.mapper.UserMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper mapper;

    @Mock
    private ArtisanRepository artisanRepository;

    @Mock
    private ArtisanMapper artisanMapper;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private UserService service;

    @Test
    void shouldLoadUserByUsername() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setRole(Role.CLIENT);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        UserDetails result = service.loadUserByUsername("user@example.com");

        assertNotNull(result);
        assertEquals("user@example.com", result.getUsername());
    }

    @Test
    void shouldThrowWhenLoadUserByUsernameNotFound() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown@example.com"));
    }

    @Test
    void shouldGetProfileAsClient() {
        User user = new User();
        user.setId(1L);
        user.setEmail("client@example.com");
        user.setRole(Role.CLIENT);

        Client client = new Client();
        client.setId(1L);
        client.setNom("Hiba");

        when(userRepository.findByEmail("client@example.com")).thenReturn(Optional.of(user));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        User result = service.getProfile("client@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldGetProfileAsArtisan() {
        User user = new User();
        user.setId(1L);
        user.setEmail("artisan@example.com");
        user.setRole(Role.ARTISAN);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        when(userRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(user));
        when(artisanRepository.findById(1L)).thenReturn(Optional.of(artisan));

        User result = service.getProfile("artisan@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateUserProfile() {
        String email = "user@example.com";

        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        dto.setNom("Hiba");

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setNom("Hiba");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(response);

        UserResponse result = service.updateUserProfile(email, dto);

        assertNotNull(result);
        assertEquals("Hiba", result.getNom());
    }

    @Test
    void shouldUpdateClientProfile() {
        String email = "client@example.com";

        ClientUpdateRequestDTO dto = new ClientUpdateRequestDTO();
        dto.setNom("Hiba");

        User user = new User();
        user.setId(1L);

        Client client = new Client();
        client.setId(1L);
        client.setNom("Hiba");

        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(1L);
        response.setNom("Hiba");
        response.setEmail("client@example.com");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(clientRepository.findById(user.getId())).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(response);

        UserResponse result = service.updateClientProfile(email, dto);

        assertNotNull(result);
        assertEquals("Hiba", result.getNom());
    }

    @Test
    void shouldUpdateArtisanProfile() {
        String email = "artisan@example.com";

        ArtisanUpdateRequestDTO dto = new ArtisanUpdateRequestDTO();
        dto.setNom("Hiba");

        User user = new User();
        user.setId(1L);

        Artisan artisan = new Artisan();
        artisan.setId(1L);
        artisan.setNom("Hiba");

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(1L);
        response.setNom("Hiba");
        response.setEmail("artisan@example.com");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(artisanMapper.toDto(artisan)).thenReturn(response);

        UserResponse result = service.updateArtisanProfile(email, dto);

        assertNotNull(result);
        assertEquals("Hiba", result.getNom());
    }
}
