package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.AvisRequestDTO;
import ServiCasa.dto.response.AvisResponseDTO;
import ServiCasa.entity.Avis;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.Reservation;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.AvisMapper;
import ServiCasa.repository.AvisRepository;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.AvisService;
import ServiCasa.service.serviceImpl.AvisServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvisServiceTest {

    @Mock
    private AvisMapper mapper;

    @Mock
    private AvisRepository avisRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ArtisanRepository artisanRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AvisServiceImpl service;

    @Test
    void shouldAddAvis() {
        AvisRequestDTO request = new AvisRequestDTO();
        request.setReservationId(1L);
        request.setNote(5);
        request.setCommentaire("Excellent");

        Client client = new Client();
        client.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setClient(client);
        reservation.setStatutReservation(StatutReservation.TERMINEE);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Avis avis = new Avis();
        avis.setId(1L);

        AvisResponseDTO response = new AvisResponseDTO();
        response.setId(1L);

        when(userRepository.findByEmail("client@example.com")).thenReturn(Optional.of(client));
        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(reservationRepository.findById(request.getReservationId())).thenReturn(Optional.of(reservation));
        when(mapper.toEntity(request)).thenReturn(avis);
        when(avisRepository.save(avis)).thenReturn(avis);
        when(mapper.toDto(avis)).thenReturn(response);

        AvisResponseDTO result = service.addAvis(request, "client@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenAddAvisNotAuthenticated() {
        AvisRequestDTO request = new AvisRequestDTO();
        request.setReservationId(1L);

        assertThrows(ResponseStatusException.class, () -> service.addAvis(request, null));
    }

    @Test
    void shouldThrowWhenAddAvisClientNotFound() {
        AvisRequestDTO request = new AvisRequestDTO();
        request.setReservationId(1L);

        when(userRepository.findByEmail("client@example.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.addAvis(request, "client@example.com"));
    }

    @Test
    void shouldGetAvisByArtisan() {
        Long artisanId = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(artisanId);

        Avis avis = new Avis();
        avis.setId(1L);

        AvisResponseDTO response = new AvisResponseDTO();
        response.setId(1L);

        when(artisanRepository.findById(artisanId)).thenReturn(Optional.of(artisan));
        when(avisRepository.findByArtisan(any(Artisan.class), any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(avis)));
        when(mapper.toDto(avis)).thenReturn(response);

        Page<AvisResponseDTO> result = service.getAvisByArtisan(artisanId, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldThrowWhenGetAvisByArtisanNotFound() {
        Long artisanId = 1L;

        when(artisanRepository.findById(artisanId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getAvisByArtisan(artisanId, mock(Pageable.class)));
    }

    @Test
    void shouldGetMoyenneArtisan() {
        Long artisanId = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(artisanId);

        when(artisanRepository.findById(artisanId)).thenReturn(Optional.of(artisan));
        when(avisRepository.getAverageNoteByArtisan(artisan)).thenReturn(4.5);

        Double result = service.getMoyenneArtisan(artisanId);

        assertEquals(4.5, result);
    }

    @Test
    void shouldGetNombreAvisArtisan() {
        Long artisanId = 1L;

        Artisan artisan = new Artisan();
        artisan.setId(artisanId);

        when(artisanRepository.findById(artisanId)).thenReturn(Optional.of(artisan));
        when(avisRepository.countByArtisan(artisan)).thenReturn(10L);

        Long result = service.getNombreAvisArtisan(artisanId);

        assertEquals(10L, result);
    }

    @Test
    void shouldExistsByReservationId() {
        Long reservationId = 1L;

        assertThrows(ResponseStatusException.class, () -> service.existsByReservationId(reservationId));
    }

    @Test
    void shouldGetAvisByReservation() {
        Long reservationId = 1L;

        assertThrows(ResponseStatusException.class, () -> service.getAvisByReservation(reservationId));
    }
}
