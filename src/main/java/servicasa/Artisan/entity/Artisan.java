package servicasa.Artisan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import servicasa.user.entity.User;

@Entity
@Table(name = "artisans")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artisan extends User {
}
