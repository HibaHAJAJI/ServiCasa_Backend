package ServiCasa.reservation.entity;

import ServiCasa.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "reservations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateReservation;

    private LocalDateTime dateIntervention;

    @Enumerated(EnumType.STRING)
    private StatutReservation statutReservation;

    private String adressIntervention;

    private String descriptionProbleme;

}