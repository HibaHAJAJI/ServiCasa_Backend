package ServiCasa.Paiement.entity;

import ServiCasa.enums.StatutPaiement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    private Double montant;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statutPaiement;

}