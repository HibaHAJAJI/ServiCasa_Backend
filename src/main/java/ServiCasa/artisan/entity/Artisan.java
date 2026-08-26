package ServiCasa.artisan.entity;

import ServiCasa.disponibilite.entity.Disponibilite;
import ServiCasa.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;
import ServiCasa.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "artisans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Artisan extends User {

    private String specialite;

    private Integer anneesExperience;

    private BigDecimal tarifHoraire;

    private String description;

    private String zoneIntervention;

    @OneToMany(mappedBy = "artisan",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Disponibilite> disponibilites;

}
