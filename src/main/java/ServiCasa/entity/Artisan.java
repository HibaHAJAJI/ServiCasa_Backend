package ServiCasa.entity;

import ServiCasa.enums.StatutCompte;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Enumerated(EnumType.STRING)
    private StatutCompte statutCompte;

    @OneToMany(mappedBy = "artisan",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Disponibilite> disponibilites;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Avis> avis;

}
