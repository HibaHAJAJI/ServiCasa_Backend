package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ServiceArtisanRequestDTO;
import ServiCasa.dto.response.ServiceArtisanResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Categorie;
import ServiCasa.entity.ServiceArtisan;
import ServiCasa.mapper.ServiceArtisanMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.repository.ServiceArtisanRepository;
import ServiCasa.service.ServiceArtisanService;
import ServiCasa.service.serviceImpl.ServiceArtisanServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceArtisanServiceTest {

    @Mock
    private ServiceArtisanMapper mapper;

    @Mock
    private ServiceArtisanRepository repository;

    @Mock
    private ArtisanRepository artisanRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private ServiceArtisanServiceImpl service;

    @Test
    void shouldCreateService() {
        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();
        request.setNom("Installation");
        request.setDescription("Installation plomberie");
        request.setTarif(java.math.BigDecimal.valueOf(100));
        request.setCategorieId(1L);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Plomberie");

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(1L);
        serviceArtisan.setArtisan(artisan);

        ServiceArtisanResponseDTO response = new ServiceArtisanResponseDTO();
        response.setId(1L);
        response.setNom("Installation");

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(artisan));
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(mapper.toEntity(request)).thenReturn(serviceArtisan);
        when(repository.save(serviceArtisan)).thenReturn(serviceArtisan);
        when(mapper.toDto(serviceArtisan)).thenReturn(response);

        ServiceArtisanResponseDTO result = service.createService(request, "artisan@example.com");

        assertNotNull(result);
        assertEquals("Installation", result.getNom());
    }

    @Test
    void shouldThrowWhenCreateServiceArtisanNotFound() {
        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.createService(request, "artisan@example.com"));
    }

    @Test
    void shouldThrowWhenCreateServiceArtisanNotAuthenticated() {
        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();

        assertThrows(ResponseStatusException.class, () -> service.createService(request, null));
    }

    @Test
    void shouldGetServicesByArtisan() {
        Long artisanId = 1L;

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(1L);

        ServiceArtisanResponseDTO response = new ServiceArtisanResponseDTO();
        response.setId(1L);

        when(repository.findByArtisanId(any(Long.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(serviceArtisan)));
        when(mapper.toDto(serviceArtisan)).thenReturn(response);

        Page<ServiceArtisanResponseDTO> result = service.getServicesByArtisan(artisanId, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldGetServiceById() {
        Long id = 1L;

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(id);

        ServiceArtisanResponseDTO response = new ServiceArtisanResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(serviceArtisan));
        when(mapper.toDto(serviceArtisan)).thenReturn(response);

        ServiceArtisanResponseDTO result = service.getServiceById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenGetServiceByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getServiceById(id));
    }

    @Test
    void shouldUpdateService() {
        Long id = 1L;

        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();
        request.setNom("Installation");

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(id);
        serviceArtisan.setArtisan(artisan);

        ServiceArtisanResponseDTO response = new ServiceArtisanResponseDTO();
        response.setId(id);

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(artisan));
        when(repository.findById(id)).thenReturn(Optional.of(serviceArtisan));
        when(mapper.toDto(serviceArtisan)).thenReturn(response);
        when(repository.save(serviceArtisan)).thenReturn(serviceArtisan);

        ServiceArtisanResponseDTO result = service.updateService(id, request, "artisan@example.com");

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateServiceNotFound() {
        Long id = 1L;

        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(new Artisan()));
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateService(id, request, "artisan@example.com"));
    }

    @Test
    void shouldThrowWhenUpdateServiceNotOwned() {
        Long id = 1L;

        ServiceArtisanRequestDTO request = new ServiceArtisanRequestDTO();

        Artisan artisan = new Artisan();
        artisan.setId(2L);

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(id);
        Artisan serviceArtisanArtisan = new Artisan();
        serviceArtisanArtisan.setId(1L);
        serviceArtisan.setArtisan(serviceArtisanArtisan);

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(artisan));
        when(repository.findById(id)).thenReturn(Optional.of(serviceArtisan));

        assertThrows(ResponseStatusException.class, () -> service.updateService(id, request, "artisan@example.com"));
    }

    @Test
    void shouldDeleteService() {
        Long id = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(id);
        serviceArtisan.setArtisan(artisan);

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(artisan));
        when(repository.findById(id)).thenReturn(Optional.of(serviceArtisan));

        service.deleteService(id, "artisan@example.com");

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteServiceNotFound() {
        Long id = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        when(artisanRepository.findByEmail("artisan@example.com")).thenReturn(Optional.of(artisan));
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.deleteService(id, "artisan@example.com"));
    }

    @Test
    void shouldGetServicesByArtisanEmail() {
        Long artisanId = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(artisanId);

        ServiceArtisan serviceArtisan = new ServiceArtisan();
        serviceArtisan.setId(1L);

        ServiceArtisanResponseDTO response = new ServiceArtisanResponseDTO();
        response.setId(1L);

        when(artisanRepository.findByEmail(any(String.class))).thenReturn(Optional.of(artisan));
        when(repository.findByArtisanId(any(Long.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(serviceArtisan)));
        when(mapper.toDto(serviceArtisan)).thenReturn(response);

        Page<ServiceArtisanResponseDTO> result = service.getServicesByArtisanEmail("artisan@example.com", mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }
}
