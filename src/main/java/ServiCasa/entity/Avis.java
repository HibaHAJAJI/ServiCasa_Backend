package ServiCasa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer note;

    private String commentaire;

    private LocalDateTime dateCreation;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

    @OneToOne()
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}