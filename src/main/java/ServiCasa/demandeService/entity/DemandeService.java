package ServiCasa.demandeService.entity;

import ServiCasa.categorie.entity.Categorie;
import ServiCasa.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "demande_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DemandeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToMany(mappedBy = "demandeService")
    private List<Reservation> reservations;



}
