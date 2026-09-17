package ServiCasa.dashboard.admin;


import ServiCasa.enums.Role;
import ServiCasa.enums.StatutReservation;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class dashboardAdminService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;


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
}
