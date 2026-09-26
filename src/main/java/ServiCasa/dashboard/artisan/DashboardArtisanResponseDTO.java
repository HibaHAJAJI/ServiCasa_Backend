package ServiCasa.dashboard.artisan;

import lombok.Data;

@Data
public class DashboardArtisanResponseDTO {

    private long nouvellesDemandes;

    private long interventionsEnCours;

    private long interventionsTerminees;

    private double revenusDuMois;
}