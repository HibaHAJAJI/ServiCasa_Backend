package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.DemandeService;
import ServiCasa.entity.Paiement;
import ServiCasa.entity.Reservation;
import ServiCasa.entity.User;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.DemandeServiceRepository;
import ServiCasa.repository.PaiementRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.notification.service.NotificationService;
import ServiCasa.service.ReservationService;
import ServiCasa.service.serviceImpl.ReservationImpl;
import ServiCasa.entity.Disponibilite;
import ServiCasa.repository.DisponibiliteRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationServiceTest {

    @Mock
    private ReservationMapper mapper;

    @Mock
    private ReservationRepository repository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ArtisanRepository artisanRepository;

    @Mock
    private DemandeServiceRepository demandeServiceRepository;

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private DisponibiliteRepository disponibiliteRepository;

    @InjectMocks
    private ReservationImpl service;

    @Test
    void shouldAddReservation() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
Long id = 1L;
        dto.setArtisanId(1L);
        dto.setClientId(1L);

        Artisan artisan = new Artisan();
        artisan.setId(id);

        Client client = new Client();
        client.setId(id);

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.EN_ATTENTE);
        reservation.setArtisan(artisan);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(artisanRepository.findById(dto.getArtisanId())).thenReturn(Optional.of(artisan));
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(mapper.toEntity(dto)).thenReturn(reservation);
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.addReservation(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenAddReservationArtisanNotFound() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setArtisanId(1L);

        when(artisanRepository.findById(dto.getArtisanId())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.addReservation(dto));
    }

    @Test
    void shouldThrowWhenAddReservationNoClient() {
        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setArtisanId(1L);
        dto.setClientId(null);
        dto.setDemandeServiceId(null);

        assertThrows(ResponseStatusException.class, () -> service.addReservation(dto));
    }

    @Test
    void shouldFindReservationById() {
        Long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.findReservationById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenFindReservationByIdNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.findReservationById(id));
    }

    @Test
    void shouldFindAllReservations() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(repository.findAll(any(Pageable.class))).thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.findAllReservations(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldUpdateReservation() {
        Long id = 1L;

        ReservationRequestDTO dto = new ReservationRequestDTO();
        dto.setArtisanId(1L);
        dto.setClientId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(id);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Client client = new Client();
        client.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(artisanRepository.findById(dto.getArtisanId())).thenReturn(Optional.of(artisan));
        when(clientRepository.findById(dto.getClientId())).thenReturn(Optional.of(client));
        when(mapper.toDto(repository.save(reservation))).thenReturn(response);

        ReservationResponseDTO result = service.updateReservation(id, dto);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldFindReservationsByClient() {
        Long clientId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(repository.findByClientId(any(Long.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.findReservationsByClient(clientId, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldGetPendingReservationsByArtisan() {
        String email = "artisan@example.com";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(repository.findByArtisanIdAndStatutReservation(any(Long.class), any(StatutReservation.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.getPendingReservationsByArtisan(email, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldGetInterventionsByArtisan() {
        String email = "artisan@example.com";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(repository.findByArtisanIdAndStatutReservationIn(eq(1L), any(List.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.getInterventionsByArtisan(email, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldGetLatestReservations() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(repository.findAllByOrderByDateReservationDesc(any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.getLatestReservations(mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldGetMyReservations() {
        String email = "client@example.com";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        Client client = new Client();
        client.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(1L);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(clientRepository.findById(user.getId())).thenReturn(Optional.of(client));
        when(repository.findByClientIdOrderByDateReservationDesc(any(Long.class), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of(reservation)));
        when(mapper.toDto(reservation)).thenReturn(response);

        Page<ReservationResponseDTO> result = service.getMyReservations(email, mock(Pageable.class));

        assertNotNull(result);
        assertEquals(1, result.getSize());
    }

    @Test
    void shouldCancelReservation() {
        Long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.EN_ATTENTE);

        Reservation saved = new Reservation();
        saved.setId(id);
        saved.setStatutReservation(StatutReservation.ANNULEE);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservation(reservation)).thenReturn(Optional.empty());
        when(repository.save(reservation)).thenReturn(saved);

        service.cancelReservation(id);

        verify(repository).save(reservation);
    }

    @Test
    void shouldThrowWhenCancelReservationAlreadyPaid() {
        Long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.EN_ATTENTE);

        Paiement paiement = new Paiement();
        paiement.setStatutPaiement(ServiCasa.enums.StatutPaiement.PAYE);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservation(reservation)).thenReturn(Optional.of(paiement));

        assertThrows(ResponseStatusException.class, () -> service.cancelReservation(id));
    }

    @Test
    void shouldThrowWhenCancelReservationNotPending() {
        Long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.ACCEPTEE);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));

        assertThrows(ResponseStatusException.class, () -> service.cancelReservation(id));
    }

@Test
    void shouldAcceptReservation() {
        Long id = 1L;
        String email = "artisan@example.com";

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.EN_ATTENTE);
        reservation.setArtisan(artisan);
        reservation.setDateIntervention(LocalDateTime.now());

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(repository.findByArtisanIdAndStatutReservationIn(any(Long.class), anyList(), any(Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl(java.util.List.of()));
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.accepterReservation(id, email);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldRefuseReservation() {
        Long id = 1L;
        String email = "artisan@example.com";

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatutReservation(StatutReservation.EN_ATTENTE);
        reservation.setArtisan(artisan);

        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.refuserReservation(id, email);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldUpdateReservationStatus() {
        Long id = 1L;
        StatutReservation statut = StatutReservation.ACCEPTEE;
        String artisanEmail = "artisan@example.com";

        Artisan artisan = new Artisan();
        artisan.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setEmail(artisanEmail);

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setArtisan(artisan);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(userRepository.findByEmail(artisanEmail)).thenReturn(Optional.of(user));
        when(artisanRepository.findById(user.getId())).thenReturn(Optional.of(artisan));
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.updateReservationStatus(id, statut, artisanEmail);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldFindReservationsByArtisan() {
        Long id = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(id);

        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(mapper.toDto(reservation)).thenReturn(response);

        ReservationResponseDTO result = service.findReservationById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
}
