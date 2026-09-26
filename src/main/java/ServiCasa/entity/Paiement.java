package ServiCasa.entity;

import ServiCasa.enums.ModePaiement;
import ServiCasa.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
    private ModePaiement modePaiement;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

    private LocalDateTime datePaiement;

    @OneToOne()
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

}