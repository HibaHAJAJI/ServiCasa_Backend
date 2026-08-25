package ServiCasa.artisan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ServiCasa.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;


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

    private LocalDate dateNaissance;

}
