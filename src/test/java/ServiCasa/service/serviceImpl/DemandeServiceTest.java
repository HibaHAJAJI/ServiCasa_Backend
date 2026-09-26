package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.DemandeServiceRequestDTO;
import ServiCasa.dto.response.DemandeServiceResponseDTO;
import ServiCasa.entity.Categorie;
import ServiCasa.entity.DemandeService;
import ServiCasa.mapper.DemandeServiceMapper;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.repository.DemandeServiceRepository;
import ServiCasa.service.DemandeServiceService;
import ServiCasa.service.serviceImpl.DemandeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemandeServiceTest {

    @Mock
    private DemandeServiceMapper mapper;

    @Mock
    private DemandeServiceRepository repository;

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private DemandeServiceImpl service;

    @Test
    void shouldAddDemandeService() {
        DemandeServiceRequestDTO request = new DemandeServiceRequestDTO();
        request.setNom("Plomberie");
        request.setDescription("Réparation");
        request.setCategorieId(1L);

        Categorie categorie = new Categorie();
        categorie.setId(1L);
        categorie.setNom("Plomberie");

        DemandeService demandeService = new DemandeService();
        demandeService.setId(1L);
        demandeService.setCategorie(categorie);

        DemandeServiceResponseDTO response = new DemandeServiceResponseDTO();
        response.setId(1L);
        response.setNom("Plomberie");

        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(mapper.toEntity(request)).thenReturn(demandeService);
        when(repository.save(demandeService)).thenReturn(demandeService);
        when(mapper.toDto(demandeService)).thenReturn(response);

        DemandeServiceResponseDTO result = service.addDemandeService(request);

        assertNotNull(result);
        assertEquals("Plomberie", result.getNom());
    }

    @Test
    void shouldThrowWhenAddDemandeServiceCategorieNotFound() {
        DemandeServiceRequestDTO request = new DemandeServiceRequestDTO();
        request.setCategorieId(1L);

        when(categorieRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.addDemandeService(request));
    }

    @Test
    void shouldFindDemandeServiceById() {
        Long id = 1L;

        DemandeService demandeService = new DemandeService();
        demandeService.setId(id);

        DemandeServiceResponseDTO response = new DemandeServiceResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(demandeService));
        when(mapper.toDto(demandeService)).thenReturn(response);

        DemandeServiceResponseDTO result = service.findDemandeServiceById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenFindDemandeServiceByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findDemandeServiceById(id));
    }

    @Test
    void shouldFindAllDemandeServices() {
        DemandeService demandeService = new DemandeService();
        demandeService.setId(1L);

        DemandeServiceResponseDTO response = new DemandeServiceResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(demandeService)));
        when(mapper.toDto(demandeService)).thenReturn(response);

        Page<DemandeServiceResponseDTO> result = service.findAllDemandeServices(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldUpdateDemandeService() {
        Long id = 1L;

        DemandeServiceRequestDTO request = new DemandeServiceRequestDTO();
        request.setNom("Plomberie");
        request.setCategorieId(1L);

        DemandeService demandeService = new DemandeService();
        demandeService.setId(id);

        Categorie categorie = new Categorie();
        categorie.setId(1L);

        DemandeServiceResponseDTO response = new DemandeServiceResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(demandeService));
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(mapper.toDto(demandeService)).thenReturn(response);
        when(repository.save(demandeService)).thenReturn(demandeService);

        DemandeServiceResponseDTO result = service.updateDemandeService(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateDemandeServiceNotFound() {
        Long id = 1L;

        DemandeServiceRequestDTO request = new DemandeServiceRequestDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateDemandeService(id, request));
    }

    @Test
    void shouldDeleteDemandeService() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteDemandeService(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteDemandeServiceNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.deleteDemandeService(id));
    }
}
