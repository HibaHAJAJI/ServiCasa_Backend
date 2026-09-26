package ServiCasa.dashboard.admin;


import lombok.Data;

@Data
public class DashboardAdminResponseDTO {

    private long totalUsers;

    private long totalClients;

    private long totalArtisans;

    private long pendingArtisans;

    private long totalReservations;

    private long pendingReservations;

    private long completedReservations;
}
