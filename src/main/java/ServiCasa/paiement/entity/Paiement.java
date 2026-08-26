package ServiCasa.paiement.entity;

import ServiCasa.enums.StatutPaiement;
import ServiCasa.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "paiements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    @OneToOne()
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

}