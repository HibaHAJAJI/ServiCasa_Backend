package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.PaiementRequestDTO;
import ServiCasa.dto.response.PaiementResponseDTO;
import ServiCasa.entity.Paiement;
import ServiCasa.entity.Reservation;
import ServiCasa.enums.ModePaiement;
import ServiCasa.enums.StatutPaiement;
import ServiCasa.mapper.PaiementMapper;
import ServiCasa.notification.service.NotificationService;
import ServiCasa.repository.PaiementRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.PaiementService;
import ServiCasa.service.serviceImpl.PaiementServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PaiementMapper paiementMapper;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaiementServiceImpl service;

    @Test
    void shouldGetAllPaiements() {
        Paiement paiement = new Paiement();
        paiement.setId(1L);

        PaiementResponseDTO response = new PaiementResponseDTO();
        response.setId(1L);

        when(paiementRepository.findAll()).thenReturn(java.util.List.of(paiement));
        when(paiementMapper.toDto(paiement)).thenReturn(response);

        List<PaiementResponseDTO> result = service.getAllPaiements();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldGetPaiementById() {
        Long id = 1L;

        Paiement paiement = new Paiement();
        paiement.setId(id);

        PaiementResponseDTO response = new PaiementResponseDTO();
        response.setId(id);

        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiement));
        when(paiementMapper.toDto(paiement)).thenReturn(response);

        PaiementResponseDTO result = service.getPaiementById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenGetPaiementByIdNotFound() {
        Long id = 1L;

        when(paiementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getPaiementById(id));
    }

    @Test
    void shouldGetPaiementByReservationId() {
        Long reservationId = 1L;

        Paiement paiement = new Paiement();
        paiement.setId(1L);

        PaiementResponseDTO response = new PaiementResponseDTO();
        response.setId(1L);

        when(paiementRepository.findByReservationId(reservationId)).thenReturn(Optional.of(paiement));
        when(paiementMapper.toDto(paiement)).thenReturn(response);

        PaiementResponseDTO result = service.getPaiementByReservationId(reservationId);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldGetPaiementByReservationIdNotFound() {
        Long reservationId = 1L;

        when(paiementRepository.findByReservationId(reservationId)).thenReturn(Optional.empty());

        PaiementResponseDTO result = service.getPaiementByReservationId(reservationId);

        assertNull(result);
    }

    @Test
    void shouldCreatePaiement() {
        PaiementRequestDTO request = new PaiementRequestDTO();
        request.setReservationId(1L);
        request.setModePaiement(ModePaiement.CASH);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPrixTotal(java.math.BigDecimal.valueOf(100));

        Paiement paiement = new Paiement();
        paiement.setId(1L);
        paiement.setStatutPaiement(StatutPaiement.PAYE);

        PaiementResponseDTO response = new PaiementResponseDTO();
        response.setId(1L);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservation(reservation)).thenReturn(Optional.empty());
        when(paiementRepository.save(any(Paiement.class))).thenReturn(paiement);
        when(paiementMapper.toDto(paiement)).thenReturn(response);

        PaiementResponseDTO result = service.createPaiement(request, "client@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenCreatePaiementReservationAlreadyPaid() {
        PaiementRequestDTO request = new PaiementRequestDTO();
        request.setReservationId(1L);
        request.setModePaiement(ModePaiement.CASH);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        Paiement existingPaiement = new Paiement();
        existingPaiement.setStatutPaiement(StatutPaiement.PAYE);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(paiementRepository.findByReservation(reservation)).thenReturn(Optional.of(existingPaiement));

        assertThrows(ResponseStatusException.class, () -> service.createPaiement(request, "client@example.com"));
    }

    @Test
    void shouldUpdatePaiement() {
        Long id = 1L;

        PaiementRequestDTO request = new PaiementRequestDTO();
        request.setModePaiement(ModePaiement.CASH);

        Paiement paiement = new Paiement();
        paiement.setId(id);
        paiement.setStatutPaiement(StatutPaiement.EN_ATTENTE);

        PaiementResponseDTO response = new PaiementResponseDTO();
        response.setId(id);

        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiement));
        when(paiementRepository.save(paiement)).thenReturn(paiement);
        when(paiementMapper.toDto(paiement)).thenReturn(response);

        PaiementResponseDTO result = service.updatePaiement(id, request, "client@example.com");

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowWhenUpdatePaiementAlreadyPaid() {
        Long id = 1L;

        PaiementRequestDTO request = new PaiementRequestDTO();

        Paiement paiement = new Paiement();
        paiement.setId(id);
        paiement.setStatutPaiement(StatutPaiement.PAYE);

        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiement));

        assertThrows(ResponseStatusException.class, () -> service.updatePaiement(id, request, "client@example.com"));
    }

    @Test
    void shouldDeletePaiement() {
        Long id = 1L;

        Paiement paiement = new Paiement();
        paiement.setId(id);

        when(paiementRepository.findById(id)).thenReturn(Optional.of(paiement));

        service.deletePaiement(id, "client@example.com");

        verify(paiementRepository).delete(paiement);
    }

    @Test
    void shouldThrowWhenDeletePaiementNotFound() {
        Long id = 1L;

        when(paiementRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.deletePaiement(id, "client@example.com"));
    }

    @Test
    void shouldCountPaiements() {
        when(paiementRepository.countByStatutPaiement(StatutPaiement.PAYE)).thenReturn(5L);

        long result = service.countPaiements();

        assertEquals(5L, result);
    }
}
