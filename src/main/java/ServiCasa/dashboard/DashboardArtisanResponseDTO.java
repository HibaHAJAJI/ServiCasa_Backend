package ServiCasa.dashboard;

import lombok.Data;

@Data
public class DashboardArtisanResponseDTO {
    private long nouvellesDemandes;
    private long interventionsEnCours;
    private long interventionsTerminees;
}
