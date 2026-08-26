package ServiCasa.artisan.dto;

import ServiCasa.disponibilite.entity.Disponibilite;
import ServiCasa.reservation.entity.Reservation;
import lombok.*;
import ServiCasa.user.dto.UserRegisterRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
public class ArtisanRequestDTO extends UserRegisterRequest {

    private String specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;


}
