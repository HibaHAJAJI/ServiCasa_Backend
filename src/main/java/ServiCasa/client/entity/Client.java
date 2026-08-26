package ServiCasa.client.entity;

import ServiCasa.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ServiCasa.user.entity.User;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {

    private String adresse;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Reservation> reservations;

}
