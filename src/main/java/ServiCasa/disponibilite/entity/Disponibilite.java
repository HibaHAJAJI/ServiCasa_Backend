package ServiCasa.disponibilite.entity;

import ServiCasa.artisan.entity.Artisan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Entity
@Table(name = "disponibilites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek jour;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private Boolean disponible;

    @ManyToOne()
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

}