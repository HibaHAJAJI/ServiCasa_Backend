package ServiCasa.dashboard.admin;


import ServiCasa.entity.Artisan;
import ServiCasa.enums.Role;
import ServiCasa.enums.StatutCompte;
import ServiCasa.enums.StatutReservation;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DashboardAdminService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private  final ArtisanRepository artisanRepository;


    public DashboardAdminResponseDTO getDashboardAdmin() {

        DashboardAdminResponseDTO dto = new DashboardAdminResponseDTO();
        dto.setTotalUsers(userRepository.count());
        dto.setTotalClients(userRepository.countByRole(Role.CLIENT));
        dto.setTotalArtisans(userRepository.countByRole(Role.ARTISAN));

        dto.setTotalReservations(reservationRepository.count());
        dto.setPendingReservations(reservationRepository.countByStatutReservation(StatutReservation.EN_ATTENTE));
        dto.setCompletedReservations(reservationRepository.countByStatutReservation(StatutReservation.TERMINEE));

        return dto;

    }

    public Page<Artisan> getArtisansByStatus(StatutCompte statut, Pageable pageable) {
        return artisanRepository.findByStatutCompte(statut, pageable);
    }

    public void updateArtisanStatus(Long id, StatutCompte nouveauStatut) {
        Artisan artisan = artisanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        artisan.setStatutCompte(nouveauStatut);
        artisanRepository.save(artisan);
    }
}
