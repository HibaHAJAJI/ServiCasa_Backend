package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ArtisanRequestDTO;
import ServiCasa.dto.response.ArtisanResponseDTO;
import ServiCasa.dto.updateDto.ArtisanUpdateRequestDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Ville;
import ServiCasa.entity.Specialite;
import ServiCasa.mapper.ArtisanMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.AvisRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.ArtisanService;
import ServiCasa.enums.Role;
import ServiCasa.service.serviceImpl.ArtisanServiceImpl;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtisanServiceTest {

    @Mock
    private ArtisanMapper mapper;

    @Mock
    private ArtisanRepository repository;

    @Mock
    private AvisRepository avisRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ArtisanServiceImpl service;

    @Test
    void shouldAddArtisan() {
        ArtisanRequestDTO request = new ArtisanRequestDTO();
        request.setNom("Hiba");
        request.setEmail("hiba@gmail.com");
        request.setTelephone("0600000000");

        Ville ville = new Ville();
        ville.setNom("Beni Mellal");
        request.setVille(ville);

        request.setPassword("password123");

        Specialite specialite = new Specialite();
        specialite.setNom("Plomberie");
        request.setSpecialite(specialite);

        request.setAnneesExperience(5);
        request.setTarifHoraire(java.math.BigDecimal.valueOf(50));
        request.setDescription("Artisan plombier");
        request.setZoneIntervention("Casablanca");

        Artisan artisan = new Artisan();
        artisan.setNom("Hiba");
        artisan.setEmail("hiba@gmail.com");
        artisan.setRole(Role.ARTISAN);

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(1L);
        response.setNom("Hiba");
        response.setEmail("hiba@gmail.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(artisan);
        when(repository.save(artisan)).thenReturn(artisan);
        when(mapper.toDto(artisan)).thenReturn(response);

        ArtisanResponseDTO result = service.addArtisan(request);

        assertEquals("Hiba", result.getNom());
        assertEquals("hiba@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowWhenAddArtisanEmailAlreadyExists() {
        ArtisanRequestDTO request = new ArtisanRequestDTO();
        request.setEmail("hiba@gmail.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.addArtisan(request));
    }

    @Test
    void shouldFindArtisanById() {
        Long id = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(id);
        artisan.setNom("Hiba");

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(id);
        response.setNom("Hiba");

        when(repository.findById(id)).thenReturn(Optional.of(artisan));
        when(mapper.toDto(artisan)).thenReturn(response);
        when(avisRepository.getAverageNoteByArtisan(artisan)).thenReturn(4.5);
        when(avisRepository.countByArtisan(artisan)).thenReturn(10L);

        ArtisanResponseDTO result = service.findArtisanById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Hiba", result.getNom());
        assertEquals(4.5, result.getMoyenneAvis());
        assertEquals(10L, result.getNombreAvis());
    }

    @Test
    void shouldThrowWhenFindArtisanByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findArtisanById(id));
    }

    @Test
    void shouldFindAllArtisans() {
        Artisan artisan = new Artisan();
        artisan.setId(1L);

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(artisan)));
        when(mapper.toDto(artisan)).thenReturn(response);
        when(avisRepository.getAverageNoteByArtisan(artisan)).thenReturn(4.5);
        when(avisRepository.countByArtisan(artisan)).thenReturn(10L);

        Page<ArtisanResponseDTO> result = service.findAllArtisans(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldUpdateArtisan() {
        Long id = 1L;

        ArtisanUpdateRequestDTO dto = new ArtisanUpdateRequestDTO();
        dto.setNom("Hiba");

        Artisan artisan = new Artisan();
        artisan.setId(id);
        artisan.setNom("Hiba");

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(id);
        response.setNom("Hiba");

        when(repository.findById(id)).thenReturn(Optional.of(artisan));
        when(repository.save(artisan)).thenReturn(artisan);
        when(mapper.toDto(artisan)).thenReturn(response);

        ArtisanResponseDTO result = service.updateArtisan(id, dto);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateArtisanNotFound() {
        Long id = 1L;

        ArtisanUpdateRequestDTO dto = new ArtisanUpdateRequestDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateArtisan(id, dto));
    }

    @Test
    void shouldDeleteArtisan() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteArtisan(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteArtisanNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.deleteArtisan(id));
    }

    @Test
    void shouldFindBySpecialiteArtisan() {
        Artisan artisan = new Artisan();
        artisan.setId(1L);

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(1L);

        when(repository.findBySpecialiteNom(any(String.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(artisan)));
        when(mapper.toDto(artisan)).thenReturn(response);
        when(avisRepository.getAverageNoteByArtisan(artisan)).thenReturn(4.5);
        when(avisRepository.countByArtisan(artisan)).thenReturn(10L);

        Page<ArtisanResponseDTO> result = service.findBySpecialiteArtisan("Plomberie", mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldFindByVilleArtisan() {
        Artisan artisan = new Artisan();
        artisan.setId(1L);

        ArtisanResponseDTO response = new ArtisanResponseDTO();
        response.setId(1L);

        when(repository.findByVilleNom(any(String.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(artisan)));
        when(mapper.toDto(artisan)).thenReturn(response);
        when(avisRepository.getAverageNoteByArtisan(artisan)).thenReturn(4.5);
        when(avisRepository.countByArtisan(artisan)).thenReturn(10L);

        Page<ArtisanResponseDTO> result = service.findByVilleArtisan("Casablanca", mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldFindAllVilles() {
        when(repository.findAllVilles()).thenReturn(List.of("Casablanca", "Rabat"));

        List<String> result = service.findAllVilles();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
