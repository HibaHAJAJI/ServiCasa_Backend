package ServiCasa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "services_artisan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceArtisan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private BigDecimal tarif;

    @ManyToOne()
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

    @ManyToOne()
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
}