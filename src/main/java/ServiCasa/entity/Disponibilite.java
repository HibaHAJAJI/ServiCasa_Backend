package ServiCasa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureDebut;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureFin;

    private Boolean disponible;

    @ManyToOne()
    @JoinColumn(name = "artisan_id")
    private Artisan artisan;

}