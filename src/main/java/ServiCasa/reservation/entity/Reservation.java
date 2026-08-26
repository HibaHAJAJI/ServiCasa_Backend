package ServiCasa.reservation.entity;

import ServiCasa.artisan.entity.Artisan;
import ServiCasa.client.entity.Client;
import ServiCasa.demandeService.entity.DemandeService;
import ServiCasa.enums.StatutReservation;
import ServiCasa.paiement.entity.Paiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    private BigDecimal prixTotal;

    @ManyToOne()
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "artisan_id", nullable = false)
    private Artisan artisan;

    @ManyToOne()
    @JoinColumn(name = "demande_service_id")
    private DemandeService demandeService;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Paiement paiement;

}