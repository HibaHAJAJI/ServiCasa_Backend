package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.DisponibiliteRequestDTO;
import ServiCasa.dto.response.DisponibiliteResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Disponibilite;
import ServiCasa.mapper.DisponibiliteMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.DisponibiliteRepository;
import ServiCasa.service.DisponibiliteService;
import ServiCasa.service.serviceImpl.DisponibiliteImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisponibiliteServiceTest {

    @Mock
    private DisponibiliteMapper mapper;

    @Mock
    private DisponibiliteRepository repository;

    @Mock
    private ArtisanRepository artisanRepository;

    @InjectMocks
    private DisponibiliteImpl service;

    @Test
    void shouldAddDisponibilite() {
        DisponibiliteRequestDTO request = new DisponibiliteRequestDTO();
        request.setArtisanId(1L);
        request.setDate(LocalDate.now());
        request.setHeureDebut(LocalTime.of(9, 0));
        request.setHeureFin(LocalTime.of(17, 0));
        request.setDisponible(true);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(1L);
        disponibilite.setArtisan(artisan);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(1L);

        when(artisanRepository.findById(1L)).thenReturn(Optional.of(artisan));
        when(mapper.toEntity(request)).thenReturn(disponibilite);
        when(repository.save(disponibilite)).thenReturn(disponibilite);
        when(mapper.toDto(disponibilite)).thenReturn(response);

        DisponibiliteResponseDTO result = service.addDisponibilite(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenAddDisponibiliteArtisanNotFound() {
        DisponibiliteRequestDTO request = new DisponibiliteRequestDTO();
        request.setArtisanId(1L);
        request.setDate(LocalDate.now());
        request.setHeureDebut(LocalTime.of(9, 0));
        request.setHeureFin(LocalTime.of(17, 0));
        request.setDisponible(true);

        when(artisanRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.addDisponibilite(request));
    }

    @Test
    void shouldThrowWhenAddDisponibiliteInvalidHours() {
        DisponibiliteRequestDTO request = new DisponibiliteRequestDTO();
        request.setArtisanId(1L);
        request.setDate(LocalDate.now());
        request.setDisponible(true);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        when(artisanRepository.findById(1L)).thenReturn(Optional.of(artisan));

        assertThrows(ResponseStatusException.class, () -> service.addDisponibilite(request));
    }

    @Test
    void shouldFindAllDisponibilites() {
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(1L);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(disponibilite)));
        when(mapper.toDto(disponibilite)).thenReturn(response);

        Page<DisponibiliteResponseDTO> result = service.findAllDisponibilites(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldFindDisponibiliteById() {
        Long id = 1L;

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(id);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(disponibilite));
        when(mapper.toDto(disponibilite)).thenReturn(response);

        DisponibiliteResponseDTO result = service.findDisponibiliteById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenFindDisponibiliteByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findDisponibiliteById(id));
    }

    @Test
    void shouldFindByArtisanId() {
        Long artisanId = 1L;

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(1L);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(1L);

        when(repository.findByArtisanId(any(Long.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(disponibilite)));
        when(mapper.toDto(disponibilite)).thenReturn(response);

        Page<DisponibiliteResponseDTO> result = service.findByArtisanId(artisanId, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldFindByArtisanIdAndDate() {
        Long artisanId = 1L;
        LocalDate date = LocalDate.now();

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(1L);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(1L);

        when(repository.findByArtisanIdAndDateAndDisponibleIsTrue(artisanId, date))
                .thenReturn(java.util.List.of(disponibilite));
        when(mapper.toDto(disponibilite)).thenReturn(response);

        List<DisponibiliteResponseDTO> result = service.findByArtisanIdAndDate(artisanId, date);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateDisponibilite() {
        Long id = 1L;

        DisponibiliteRequestDTO request = new DisponibiliteRequestDTO();
        request.setDate(LocalDate.now());
        request.setHeureDebut(LocalTime.of(9, 0));
        request.setHeureFin(LocalTime.of(17, 0));
        request.setDisponible(true);

        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(id);

        DisponibiliteResponseDTO response = new DisponibiliteResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(disponibilite));
        when(repository.save(disponibilite)).thenReturn(disponibilite);
        when(mapper.toDto(disponibilite)).thenReturn(response);

        DisponibiliteResponseDTO result = service.updateDisponibilite(request, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateDisponibiliteNotFound() {
        Long id = 1L;

        DisponibiliteRequestDTO request = new DisponibiliteRequestDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateDisponibilite(request, id));
    }

    @Test
    void shouldDeleteDisponibilite() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteDisponibilite(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteDisponibiliteNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.deleteDisponibilite(id));
    }
}
