package ServiCasa.auth.service.impl;

import ServiCasa.auth.dto.AuthRequestDTO;
import ServiCasa.auth.dto.AuthResponseDTO;
import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.request.ClientRequestDTO;
import ServiCasa.dto.request.UserRegisterRequest;
import ServiCasa.dto.response.UserResponse;
import ServiCasa.dto.updateDto.UserUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.User;
import ServiCasa.enums.Role;
import ServiCasa.enums.StatutCompte;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.mapper.ClientMapper;
import ServiCasa.mapper.UserMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.security.JwtService;
import ServiCasa.auth.service.AuthService;
import ServiCasa.auth.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mapstruct.MappingTarget;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserMapper userMapper = Mockito.spy(new UserMapper() {
        @Override
        public User toEntity(UserRegisterRequest dto) { return new User(); }
        @Override
        public UserResponse toDto(User user) { return null; }
        @Override
        public void updateUserDto(UserUpdateRequestDTO dto, @MappingTarget User user) {}
    });
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final JwtService jwtService = mock(JwtService.class);
    private final ArtisanMapper artisanMapper = mock(ArtisanMapper.class);
    private final ArtisanRepository artisanRepository = mock(ArtisanRepository.class);
    private final ClientMapper clientMapper = mock(ClientMapper.class);
    private final ClientRepository clientRepository = mock(ClientRepository.class);
    private final AuthServiceImpl service = new AuthServiceImpl(
            userRepository, userMapper, passwordEncoder, authenticationManager,
            jwtService, artisanMapper, artisanRepository, clientMapper, clientRepository
    );

    @Test
    void shouldLogin() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setEmail("user@example.com");
        request.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setRole(Role.CLIENT);

        AuthResponseDTO response = new AuthResponseDTO("jwt-token");

        doReturn(null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthResponseDTO result = service.login(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
    }

    @Test
    void shouldLoginAsArtisan() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setEmail("artisan@example.com");
        request.setPassword("password123");

        Artisan artisan = new Artisan();
        artisan.setId(1L);
        artisan.setEmail("artisan@example.com");
        artisan.setStatutCompte(StatutCompte.ACCEPTE);
        artisan.setRole(Role.ARTISAN);

        AuthResponseDTO response = new AuthResponseDTO("jwt-token");

        doReturn(null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(artisan));
        when(jwtService.generateToken(artisan)).thenReturn("jwt-token");

        AuthResponseDTO result = service.login(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
    }

    @Test
    void shouldThrowWhenLoginArtisanPending() {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setEmail("artisan@example.com");
        request.setPassword("password123");

        Artisan artisan = new Artisan();
        artisan.setId(1L);
        artisan.setEmail("artisan@example.com");
        artisan.setStatutCompte(StatutCompte.EN_ATTENTE);
        artisan.setRole(Role.ARTISAN);

        doReturn(null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(artisan));

        assertThrows(ResponseStatusException.class, () -> service.login(request));
    }

    @Test
    void shouldRegisterArtisan() {
        ArtisanRequestDTO request = new ArtisanRequestDTO();
        request.setNom("Hiba");
        request.setEmail("artisan@example.com");
        request.setTelephone("0600000000");
        request.setVille("Casablanca");
        request.setPassword("password123");
        request.setSpecialite("Plomberie");
        request.setAnneesExperience(5);
        request.setTarifHoraire(java.math.BigDecimal.valueOf(50));
        request.setDescription("Artisan");
        request.setZoneIntervention("Casablanca");

        Artisan artisan = new Artisan();
        artisan.setId(1L);
        artisan.setEmail("artisan@example.com");
        artisan.setRole(Role.ARTISAN);
        artisan.setStatutCompte(StatutCompte.EN_ATTENTE);

        AuthResponseDTO response = new AuthResponseDTO("jwt-token");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(artisanMapper.toEntity(request)).thenReturn(artisan);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(artisanRepository.save(artisan)).thenReturn(artisan);
        when(jwtService.generateToken(artisan)).thenReturn("jwt-token");

        AuthResponseDTO result = service.registerArtisan(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
    }

    @Test
    void shouldThrowWhenRegisterArtisanEmailExists() {
        ArtisanRequestDTO request = new ArtisanRequestDTO();
        request.setEmail("artisan@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Artisan()));

        assertThrows(ResponseStatusException.class, () -> service.registerArtisan(request));
    }

    @Test
    void shouldRegisterClient() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setNom("Hiba");
        request.setEmail("client@example.com");
        request.setTelephone("0600000000");
        request.setVille("Casablanca");
        request.setPassword("password123");
        request.setAdresse("123 rue");

        Client client = new Client();
        client.setId(1L);
        client.setEmail("client@example.com");
        client.setRole(Role.CLIENT);

        AuthResponseDTO response = new AuthResponseDTO("jwt-token");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(clientMapper.toEntity(request)).thenReturn(client);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(clientRepository.save(client)).thenReturn(client);
        when(jwtService.generateToken(client)).thenReturn("jwt-token");

        AuthResponseDTO result = service.registerClient(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
    }

    @Test
    void shouldThrowWhenRegisterClientEmailExists() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setEmail("client@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Client()));

        assertThrows(ResponseStatusException.class, () -> service.registerClient(request));
    }

    @Test
    void shouldRegisterAdmin() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setNom("Admin");
        request.setEmail("admin@example.com");
        request.setTelephone("0600000000");
        request.setVille("Casablanca");
        request.setPassword("password123");
        request.setRole(Role.ADMIN);

        User user = new User();
        user.setId(1L);
        user.setEmail("admin@example.com");
        user.setRole(Role.ADMIN);

        AuthResponseDTO response = new AuthResponseDTO("jwt-token");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        doReturn(user).when(userMapper).toEntity(request);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        AuthResponseDTO result = service.registerAdmin(request);

        assertNotNull(result);
        assertEquals("jwt-token", result.getToken());
    }
}
