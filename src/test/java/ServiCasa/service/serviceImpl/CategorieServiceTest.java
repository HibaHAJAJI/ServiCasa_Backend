package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.CategorieRequestDTO;
import ServiCasa.dto.response.CategorieResponseDTO;
import ServiCasa.entity.Categorie;
import ServiCasa.mapper.CategorieMapper;
import ServiCasa.repository.CategorieRepository;
import ServiCasa.service.CategorieService;
import ServiCasa.service.serviceImpl.CategorieImpl;
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
class CategorieServiceTest {

    @Mock
    private CategorieMapper mapper;

    @Mock
    private CategorieRepository repository;

    @InjectMocks
    private CategorieImpl service;

    @Test
    void shouldAddCategorie() {
        CategorieRequestDTO request = new CategorieRequestDTO();
        request.setNom("Plomberie");

        Categorie categorie = new Categorie();
        categorie.setNom("Plomberie");

        CategorieResponseDTO response = new CategorieResponseDTO();
        response.setId(1L);
        response.setNom("Plomberie");

        when(mapper.toEntity(request)).thenReturn(categorie);
        when(repository.save(categorie)).thenReturn(categorie);
        when(mapper.toDto(categorie)).thenReturn(response);

        CategorieResponseDTO result = service.addCategorie(request);

        assertEquals("Plomberie", result.getNom());
    }

    @Test
    void shouldFindCategorieById() {
        Long id = 1L;

        Categorie categorie = new Categorie();
        categorie.setId(id);
        categorie.setNom("Plomberie");

        CategorieResponseDTO response = new CategorieResponseDTO();
        response.setId(id);
        response.setNom("Plomberie");

        when(repository.findById(id)).thenReturn(Optional.of(categorie));
        when(mapper.toDto(categorie)).thenReturn(response);

        CategorieResponseDTO result = service.findCategorieById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Plomberie", result.getNom());
    }

    @Test
    void shouldThrowWhenFindCategorieByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findCategorieById(id));
    }

    @Test
    void shouldFindAllCategories() {
        Categorie categorie = new Categorie();
        categorie.setId(1L);

        CategorieResponseDTO response = new CategorieResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(categorie)));
        when(mapper.toDto(categorie)).thenReturn(response);

        Page<CategorieResponseDTO> result = service.findAllCategories(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldUpdateCategorie() {
        Long id = 1L;

        CategorieRequestDTO request = new CategorieRequestDTO();
        request.setNom("Plomberie");

        Categorie categorie = new Categorie();
        categorie.setId(id);
        categorie.setNom("Plomberie");

        CategorieResponseDTO response = new CategorieResponseDTO();
        response.setId(id);
        response.setNom("Plomberie");

        when(repository.findById(id)).thenReturn(Optional.of(categorie));
        when(mapper.toDto(categorie)).thenReturn(response);
        when(repository.save(categorie)).thenReturn(categorie);

        CategorieResponseDTO result = service.updateCategorie(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdateCategorieNotFound() {
        Long id = 1L;

        CategorieRequestDTO request = new CategorieRequestDTO();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateCategorie(id, request));
    }

    @Test
    void shouldDeleteCategorie() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteCategorie(id);

        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeleteCategorieNotFound() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.deleteCategorie(id));
    }
}
